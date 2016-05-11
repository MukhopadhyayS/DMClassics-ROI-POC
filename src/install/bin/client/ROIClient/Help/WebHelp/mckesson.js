// V2.2.3

var goTopicFrame = top;

// Returns to the previous topic, if one exists.
//
function fBackButton() {
  if (goTopicFrame.name == "_99_115_104" || goTopicFrame.name == "csh") {
    goTopicFrame = top.frames[0].frames[1].frames[1];
  }
  else if (goTopicFrame.name == "bsscright") {
    goTopicFrame = goTopicFrame;
  }
  else {
    goTopicFrame = top.frames[1].frames[1];
  }

  // Be sure the document in the frame completely loaded.
  if (goTopicFrame.location) {
    goTopicFrame.history.back();
  }
}

// Prints the topic in the topic frame. Needed because it would print the TOC
// if that was the last frame the user clicked in.
//
function fPrintTopic() {
  if (goTopicFrame.name == "_99_115_104" || goTopicFrame.name == "csh") {
    goTopicFrame = top.frames[0].frames[1].frames[1];
  }
  else if (goTopicFrame.name == "bsscright") {
    goTopicFrame = goTopicFrame;
  }
  else {
    goTopicFrame = top.frames[1].frames[1];
  }

  // Be sure the document in the frame completely loaded.
  if (goTopicFrame.location) {
    // Setting the focus is needed by IE.
    goTopicFrame.focus();
    goTopicFrame.print();
  }
}
