// Copyright © 2009 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.

AddRevisionMarks();

function AddRevisionMarks() {
// Main function for processing revision marks.

var bInParaBlock = false;
var sParaText = "";
var aParaSegment = new Array;
var sNewParaText = "";
var bInParaRange = false;
var bEndOfRange = false;

// Process each para individually. Processing the entire document body
// causes a conflict with WebHelp in IE (but not in Firefox).
var aContent = document.body.getElementsByTagName("*");
for (var i = 0; i < aContent.length; i++) {

  // Process only block nodes that can contain text or graphics in our
  // RoboHelp projects. If a TD or LI contains no P, don't include it
  // because they all should have a P.
  switch (aContent[i].nodeName) {
    case "P" :
      bInParaBlock = true;
      break;
    case "H1" :
      bInParaBlock = true;
      break;
    case "H2" :
      bInParaBlock = true;
      break;
    case "H3" :
      bInParaBlock = true;
      break;
    default :
      bInParaBlock = false;
  }

  // Process the node only if it has child nodes in case a writer inserts
  // an empty paragraph.
  if (bInParaBlock == true && aContent[i].hasChildNodes() == true) {

    // Get the node content in HTML string format.
    sNewParaText = "";
    sParaText = aContent[i].innerHTML;

    // Split the para text into an array of segments based on revision
    // start "tags". The segments don't contain the split separator value,
    // which is the revision start "tag".
    aParaSegment = sParaText.split("[RevisionStart]");

    // Add the <ins> tag at the end of each segment, but omit it at the 
    // end of the last one.
    for (var j = 0; j < aParaSegment.length; j++) {
      if (j != aParaSegment.length - 1) {
        aParaSegment[j] = InsertStartTag(aParaSegment[j]);
      }
      // Create a new string that combines all the segments with the <ins> tags.
      sNewParaText = sNewParaText + aParaSegment[j];
    }

    // Repeat the above process but on the new string and looking for 
    // revision end "tags".
    aParaSegment = sNewParaText.split("[RevisionEnd]");
    // Reset sNewParaText so can rewrite it.
    sNewParaText = "";
    for (var j = 0; j < aParaSegment.length; j++) {
      if (j != aParaSegment.length - 1) {
        aParaSegment[j] = InsertEndTag(aParaSegment[j]);
      }
      sNewParaText = sNewParaText + aParaSegment[j];
    }

    // Remove span ending tags after <ins> and </ins>.
    sNewParaText = RemoveSpanEndTags(sNewParaText);

    // If the paragraph has no </ins> tag, process subsequent paras until
    // one with </ins> occurs. This is needed to apply revision marks to
    // every paragraph in a revision range.
    if (bInParaRange == false) {
      bInParaRange = TestParaRangeStart(sNewParaText);
      if (bInParaRange == true) {
        // Add an end tag to complete the tag set for the para.
        sNewParaText = IncludeParaRange(sNewParaText, "AddEndTag");
      }
    }
    else if (bInParaRange == true) {
      bEndOfRange = TestParaRangeEnd(sNewParaText);
      if (bEndOfRange == true) {
        // Add a start tag to complete the tag set for the para.
        sNewParaText = IncludeParaRange(sNewParaText, "AddStartTag");
        bInParaRange = false;
      }
      else {
        // Add start and end tags to include the para in the range.
        sNewParaText = IncludeParaRange(sNewParaText, "AddBothTags");
      }
    }

    // Assign the modified para text to the original para node.
    aContent[i].innerHTML = sNewParaText;
  }
}

// Display a border line on block elements that have a revision.
AddChangeBars();
}

function RemoveSpanEndTags(sText) {
// Removes </span> tags that end the <span> tags that enclose the revision tags
// when writers apply a condition or style to the revision-tag variables.

var nSpanEndingTag = 0;
var sRemainingText = "";

// Remove from all <ins> tags.
while (sText.indexOf("<ins></SPAN>") != -1) {
  nSpanEndingTag = sText.indexOf("<ins></SPAN>");
  sRemainingText = sText.substring(nSpanEndingTag + 12);
  sText = sText.substring(0, nSpanEndingTag + 5);
  sText = sText + sRemainingText;
}

// Remove from all </ins> tags.
while (sText.indexOf("</ins></SPAN>") != -1) {
  nSpanEndingTag = sText.indexOf("</ins></SPAN>");
  sRemainingText = sText.substring(nSpanEndingTag + 13);
  sText = sText.substring(0, nSpanEndingTag + 6);
  sText = sText + sRemainingText;
}

return sText;
}

function InsertStartTag(sText) {
// Removes <span> tags that enclose the revision start tags when writers apply
// a condition or style to the revision-tag variables. Then inserts the <ins>
// start tag.

var nSpanStart = 0;

// Remove any span starting tag that may precede the <ins> tag.
nSpanStart = sText.indexOf("x-condition: Revision\">");
if (nSpanStart != -1) {
  var sChar = "";
  for (var k = nSpanStart; k > -1; k--) {
    sChar = sText.substr(k,1);

    // If the first element in the para is a span tag for the revision tag,
    // return an empty string. Else return the text without the span tag.
    if (k == 0 && sChar == "<") {
      sText = sText.substring(0,k);
      break;
    }
    else if (sChar == "<") {
      sText = sText.substring(0,k);
      break;
    }
  }
}

sText = sText + "<ins>";
return sText;
}

function InsertEndTag(sText) {
// Removes <span> tags that enclose the revision end tags when writers apply a 
// condition or style to the revision-tag variables. Then inserts the </ins>
// end tag.

var nSpanEnd = 0;

// Remove any span starting tag that may precede the </ins> tag.
nSpanStart = sText.indexOf("x-condition: Revision\">");
if (nSpanStart != -1) {
  var sChar = "";
  for (var k = nSpanStart; k > -1; k--) {
    sChar = sText.substr(k,1);

    // If the first element in the para is a span tag for the revision tag,
    // return an empty string. Else return the text without the span tag.
    if (k == 0 && sChar == "<") {
      sText = sText.substring(0,k);
      break;
    }
    else if (sChar == "<") {
      sText = sText.substring(0,k);
      break;
    }
  }
}

sText = sText + "</ins>";
return sText;
}

function TestParaRangeStart(sText) {
// Determines if a paragraph has an open <ins> tag (no </ins> end tag).

var bHasOpenStartTag = false;
var nTagStart = 0;
var nStartTagCount = 0;
var nEndTagCount = 0;

while (sText.indexOf("<ins>") != -1) {
  nTagStart = sText.indexOf("<ins>");
  sText = sText.substring(nTagStart + 5);
  nStartTagCount++;
}

while (sText.indexOf("</ins>") != -1) {
  nTagStart = sText.indexOf("<ins>");
  sText = sText.substring(nTagStart + 5);
  nEndTagCount++;
}

if (nStartTagCount > nEndTagCount) {
  bHasOpenStartTag = true;
}

return bHasOpenStartTag;
}

function TestParaRangeEnd(sText) {
// Determines if a paragraph has an open </ins> tag (no <ins> start tag).

var bHasOpenEndTag = false;
var nTagStart = 0;
var nStartTagCount = 0;
var nEndTagCount = 0;

while (sText.indexOf("<ins>") != -1) {
  nTagStart = sText.indexOf("<ins>");
  sText = sText.substring(nTagStart + 5);
  nStartTagCount++;
}

while (sText.indexOf("</ins>") != -1) {
  nTagStart = sText.indexOf("<ins>");
  sText = sText.substring(nTagStart + 5);
  nEndTagCount++;
}

if (nEndTagCount > nStartTagCount) {
  bHasOpenEndTag = true;
}

return bHasOpenEndTag;
}

function IncludeParaRange(sText, sType) {
// Adds <ins> and </ins> tags as needed to be sure the paragraph has a
// complete set. It's not valid HTML to have those tags cross boundaries of
// block elements, so every paragraph in a revision range needs its own set
// of tags.

if (sType == "AddStartTag") {
  sText = "<ins>" + sText;
}
else if (sType == "AddEndTag") {
  sText = sText + "</ins>";
}
else if (sType == "AddBothTags") {
  sText = "<ins>" + sText + "</ins>";
}

return sText;
}

function AddChangeBars() {
// Adds a border to each block element that has a revision.
// These aren't needed in online help except on images because <ins>
// doesn't work on them.

var aContent = document.body.getElementsByTagName("IMG");
for (var i = 0; i < aContent.length; i++) {
  if (aContent[i].parentNode.nodeName == "INS") {
    aContent[i].style.borderBottom = "solid";
    aContent[i].style.borderBottomWidth = "1pt";
    aContent[i].style.borderBottomColor = "#ce084d";
    aContent[i].style.paddingBottom = "1pt";

    // This one doesn't work in IE6, but it does display the value with getAttribute().
//   aContent[i].setAttribute("class","Revision");
    // This one works in IE6 but displays null with getAttribute().
//    aContent[i].className = "Revision";
//alert("Class: (" + aContent[i].getAttribute('class') + ")");
    // This one doesn't work in IE6.
//    aContent[i].setAttribute("style","border-bottom:solid; border-bottom-width:1pt; padding-bottom:4pt");
//alert("Style: (" + aContent[i].style.borderBottom + ")");
  }
}
}
