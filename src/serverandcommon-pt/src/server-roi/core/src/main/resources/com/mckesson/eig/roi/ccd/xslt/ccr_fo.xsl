<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet exclude-result-prefixes="a date str" version="1.0"
xmlns:a="urn:astm-org:CCR"
xmlns:date="http://exslt.org/dates-and-times"
xmlns:str="http://exslt.org/strings"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output version="1.0" method="xml" encoding="ISO-8859-1" indent="no"/>
	<xsl:attribute-set name="table.header.properties">
		<xsl:attribute name="padding">3pt</xsl:attribute>
		<xsl:attribute name="border">1pt solid blue</xsl:attribute>
		<xsl:attribute name="background-color">#E3E3E3</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="table.body.properties">
		<xsl:attribute name="padding">3pt</xsl:attribute>
		<xsl:attribute name="border">1pt solid blue</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>
		<xsl:attribute-set name="title.properties">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="font-size">14pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>
		<xsl:attribute-set name="section.header.properties">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="font-size">12pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>
		<xsl:attribute-set name="table.properties">
		<xsl:attribute name="border">0.5pt solid black</xsl:attribute>
		<xsl:attribute name="border-spacing">3pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="page.number.properties">
		<xsl:attribute name="text-align">end</xsl:attribute>
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>

	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="ccr-page" page-height="11in" 
					page-width="8.5in" margin-top="0.5in" margin-bottom="0.5in" 
					margin-left="0.5in" margin-right="0.5in">
					<fo:region-body margin-top="0.5in" />
					<fo:region-before extent="0.5in" />
					<fo:region-after extent="0.5in" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="ccr-page">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block xsl:use-attribute-sets="page.number.properties">
						Page: <fo:page-number />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block xsl:use-attribute-sets="title.properties">Continuity of Care Record</fo:block>
					<fo:block><fo:leader /></fo:block>
					<xsl:call-template name="displayPurpose" />
					<xsl:call-template name="displayDemographics" />
					<xsl:call-template name="displayAlerts" />
					<xsl:call-template name="displayAdvanceDirectives" />
					<xsl:call-template name="displaySupportProviders" />
					<xsl:call-template name="displayFunctionalStatus" />
					<xsl:call-template name="displayProblems" />
					<xsl:call-template name="displayProcedures" />
					<xsl:call-template name="displayMedications" />
					<xsl:call-template name="displayImmunizations" />
					<xsl:call-template name="displayVitalSigns" />
					<xsl:call-template name="displayEncounters" />
					<xsl:call-template name="displaySocialHistory" />
					<xsl:call-template name="displayFamilyHistory" />
					<xsl:call-template name="displayResultsDiscrete" />
					<xsl:call-template name="displayResultsReport" />
					<xsl:call-template name="displayInsurance" />
					<xsl:call-template name="displayPlanOfCare" />
					<xsl:call-template name="displayHealthCareProviders" />
					<xsl:call-template name="displayReferences" />
					<xsl:call-template name="displayAdditionalInformationPeople" />
					<xsl:call-template name="displayAdditionalInformationOrganization" />
					<xsl:call-template name="displayInformationSystem" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<!-- actor.xsl -->
	<!-- Returns the name of the actor, if there is no name it returns the ActorObjectID that was passed in -->
	<xsl:template name="actorName">
		<xsl:param name="objID" />
		<xsl:for-each select="/a:ContinuityOfCareRecord/a:Actors/a:Actor">
			<xsl:variable name="thisObjID" select="a:ActorObjectID" />
			<xsl:if test="$objID = $thisObjID">
				<xsl:choose>
					<xsl:when test="a:Person">
						<xsl:choose>
							<xsl:when test="a:Person/a:Name/a:DisplayName">
								<xsl:value-of select="a:Person/a:Name/a:DisplayName" />
							</xsl:when>
							<xsl:when test="a:Person/a:Name/a:CurrentName">
								<xsl:value-of select="a:Person/a:Name/a:CurrentName/a:Given" />
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:CurrentName/a:Middle"/>
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:CurrentName/a:Family"/>
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:CurrentName/a:Suffix"/>
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:CurrentName/a:Title"/>
								<xsl:text xml:space="preserve"> </xsl:text>
							</xsl:when>
							<xsl:when test="a:Person/a:Name/a:BirthName">
								<xsl:value-of select="a:Person/a:Name/a:BirthName/a:Given" />
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:BirthName/a:Middle" />
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:BirthName/a:Family" />
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:BirthName/a:Suffix" />
								<xsl:text xml:space="preserve"> </xsl:text>
								<xsl:value-of select="a:Person/a:Name/a:BirthName/a:Title" />
								<xsl:text xml:space="preserve"> </xsl:text>
							</xsl:when>
							<xsl:when test="a:Person/a:Name/a:AdditionalName">
								<xsl:for-each select="a:Person/a:Name/a:AdditionalName">
									<xsl:value-of select="a:Given" />
									<xsl:text xml:space="preserve"> </xsl:text>
									<xsl:value-of select="a:Middle" />
									<xsl:text xml:space="preserve"> </xsl:text>
									<xsl:value-of select="a:Family" />
									<xsl:text xml:space="preserve"> </xsl:text>
									<xsl:value-of select="a:Suffix" />
									<xsl:text xml:space="preserve"> </xsl:text>
									<xsl:value-of select="a:Title" />
									<xsl:text xml:space="preserve"> </xsl:text>
									<xsl:if test="position() != last()">
										<fo:block />
									</xsl:if>
								</xsl:for-each>
							</xsl:when>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="a:Organization">
						<xsl:value-of select="a:Organization/a:Name" />
					</xsl:when>
					<xsl:when test="a:InformationSystem">
						<xsl:value-of select="a:InformationSystem/a:Name" />
						<xsl:text xml:space="preserve"> </xsl:text>
						<xsl:if test="a:InformationSystem/a:Version">
							<xsl:value-of select="a:InformationSystem/a:Version" />
							<xsl:text xml:space="preserve"> </xsl:text>
						</xsl:if>
						<xsl:if test="a:InformationSystem/a:Type">( 
							<xsl:value-of select="a:InformationSystem/a:Type" />)
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$objID" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
  
	<xsl:template match="a:Directions">
		<xsl:for-each select="a:Direction">
			<xsl:choose>
				<xsl:when test="a:Description/a:Text">
					<xsl:value-of select="a:Description/a:Text"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="a:Dose/a:Value"/>
					<xsl:text xml:space="preserve"> </xsl:text>
					<xsl:value-of select="a:Dose/a:Units/a:Unit"/>
					<xsl:text xml:space="preserve"> </xsl:text>
					<xsl:value-of select="a:Route/a:Text"/>
					<xsl:text xml:space="preserve"> </xsl:text>
					<xsl:value-of select="a:Frequency/a:Value"/>
					<xsl:if test="a:Duration">
						<xsl:text xml:space="preserve"> </xsl:text>(for <xsl:value-of select="a:Duration/a:Value"/>
						<xsl:text xml:space="preserve"> </xsl:text>
						<xsl:value-of select="a:Duration/a:Units/a:Unit"/>)
					</xsl:if>
					<xsl:if test="a:MultipleDirectionModifier/a:ObjectAttribute">
						<xsl:for-each select="a:MultipleDirectionModifier/a:ObjectAttribute">
						  <xsl:value-of select="a:Attribute"/>
						  <fo:block />
						  <xsl:value-of select="a:AttributeValue/a:Value"/>
						</xsl:for-each>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<fo:block />
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="writeDateOfBirth">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:choose>
						<xsl:when test="$objID/a:ExactDateTime">
							<xsl:value-of select="$objID/a:ExactDateTime" /> 
						</xsl:when>
						<xsl:when test="$objID/a:ApproximateDateTime">
							<xsl:value-of select="$objID/a:ApproximateDateTime" /> 
						</xsl:when>
					</xsl:choose>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeDate">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:for-each select="$objID/a:DateTime">
						<xsl:if test="a:Type/a:Text">
							<xsl:value-of select="a:Type/a:Text" /> : 
						</xsl:if>
						<xsl:if test="a:ExactDateTime">
							<xsl:value-of select="a:ExactDateTime" /> 
						</xsl:if>
						<xsl:if test="a:ApproximateDateTime">
							<xsl:value-of select="a:ApproximateDateTime" />  
						</xsl:if>
						<xsl:if test="a:Age">
							<xsl:value-of select="a:Age/a:Value"/>
							<xsl:text xml:space="preserve"> </xsl:text>
							<xsl:value-of select="a:Age/a:Units/a:Unit"/>
						</xsl:if>
						<fo:block />
					</xsl:for-each>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
  
	<xsl:template name="writeActorName">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:call-template name="actorName">
						<xsl:with-param name="objID">
							<xsl:value-of select="$objID" />
						</xsl:with-param>
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeText">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:value-of select="$objID/a:Text" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeActor">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:for-each select="$objID/a:ActorLink">
						<xsl:call-template name="actorName">
							<xsl:with-param name="objID" select="a:ActorID" />
						</xsl:call-template>
						<xsl:if test="a:ActorRole/a:Text">
						<xsl:text xml:space="preserve">(</xsl:text>
							<xsl:value-of select="a:ActorRole/a:Text" />
						<xsl:text>)</xsl:text>
						</xsl:if>
						<fo:block />
					</xsl:for-each>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeTitle">
		<xsl:param name="title"/>
    	<fo:block xsl:use-attribute-sets="section.header.properties">
			<xsl:value-of select="$title" />
    	</fo:block>
	</xsl:template>

	<xsl:template name="writeMessage">
		<xsl:param name="title"/>
		<fo:table xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="7in" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<fo:block>
							<xsl:value-of select="$title" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<fo:block>
			<fo:leader />
		</fo:block>
	</xsl:template>

	<xsl:template name="writeAddress">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:for-each select="a:Address">
						<xsl:if test="a:Type">
							<xsl:value-of select="a:Type/a:Text" />: 
							<fo:block />
						</xsl:if>
						<xsl:if test="a:Line1">
							<xsl:value-of select="a:Line1" />
							<fo:block />
						</xsl:if>
						<xsl:if test="a:Line2">
							<xsl:value-of select="a:Line2" />
							<fo:block />
						</xsl:if>
						<xsl:if test="a:City">
							<xsl:value-of select="a:City" />,
						</xsl:if>
						<xsl:value-of select="a:State" />
						<xsl:value-of select="a:PostalCode" />
						<fo:block />
					</xsl:for-each>
					<xsl:for-each select="a:Telephone">
						<xsl:if test="a:Type/a:Text">
							<xsl:value-of select="a:Type/a:Text" />:
						</xsl:if>
						<xsl:value-of select="a:Value" />
						<fo:block />
					</xsl:for-each>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeCode">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:apply-templates select="$objID/a:Code"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="writeSource">
		<xsl:param name="objID"/>
		<xsl:param name="title"/>
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="table.header.properties">
				<fo:block><xsl:value-of select="$title" /></fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="table.body.properties">
				<fo:block>
					<xsl:call-template name="actorName">
						<xsl:with-param name="objID" select="$objID/a:Actor/a:ActorID"/>
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="a:Code">
		<xsl:value-of select="a:Value" />
		<xsl:if test="a:CodingSystem">
			<xsl:text xml:space="preserve" />( 
			<xsl:value-of select="a:CodingSystem" />)
		</xsl:if>
		<fo:block />
	</xsl:template>

  <!-- problemDescription.xsl -->
  <!-- Returns the description of the problem, if there is no name it returns the ObjectID that was passed in -->
	<xsl:template name="problemDescription">
		<xsl:param name="objID"/>
		<xsl:for-each select="/a:ContinuityOfCareRecord/a:Body/a:Problems/a:Problem">
			<xsl:variable name="thisObjID" select="a:CCRDataObjectID"/>
			<xsl:if test="$objID = $thisObjID">
				<xsl:choose>
					<xsl:when test="a:Description/a:Text">
						<xsl:value-of select="a:Description/a:Text"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$objID"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
 
	<xsl:template name="displayPurpose">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Purpose'"/>
		</xsl:call-template>
		<fo:table xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="2in" />
			<fo:table-column column-width="5in" />
			<fo:table-body>
				<xsl:call-template name="writeDate">
					<xsl:with-param name="objID" select="a:ContinuityOfCareRecord"/>
					<xsl:with-param name="title" select="'Date Created:'"/>
				</xsl:call-template>
				<xsl:call-template name="writeActor">
					<xsl:with-param name="objID" select="a:ContinuityOfCareRecord/a:From"/>
					<xsl:with-param name="title" select="'From:'"/>
				</xsl:call-template>
				<xsl:call-template name="writeActor">
					<xsl:with-param name="objID" select="a:ContinuityOfCareRecord/a:To"/>
					<xsl:with-param name="title" select="'To:'"/>
				</xsl:call-template>
				<xsl:call-template name="writeText">
					<xsl:with-param name="objID" select="a:ContinuityOfCareRecord/a:Purpose/a:Description"/>
					<xsl:with-param name="title" select="'Purpose:'"/>
				</xsl:call-template>
			</fo:table-body>
		</fo:table>
		<fo:block>
			<fo:leader />
		</fo:block>
	</xsl:template>
  
	<xsl:template name="displayDemographics">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Patient Demographics'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Patient">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Patient">
					<xsl:variable name="objID" select="a:ActorID" />
					<xsl:variable name="count" select="/a:ContinuityOfCareRecord/a:Actors/a:Actor/a:ActorObjectID[node()=$objID]"/>
					<xsl:choose>
						<xsl:when test="count($count)&gt;0">
							<xsl:for-each select="/a:ContinuityOfCareRecord/a:Actors/a:Actor">
								<xsl:variable name="thisObjID" select="a:ActorObjectID" />
								<xsl:if test="$objID = $thisObjID">
									<xsl:variable name="hasActor" select="1" />
									<fo:table xsl:use-attribute-sets="table.properties">
										<fo:table-column column-width="2in" />
										<fo:table-column column-width="5in" />
										<fo:table-body>
											<xsl:call-template name="writeActorName">
												<xsl:with-param name="objID" select="a:ActorObjectID"/>
												<xsl:with-param name="title" select="'Name'"/>
											</xsl:call-template>
											<xsl:call-template name="writeDateOfBirth">
												<xsl:with-param name="objID" select="a:Person/a:DateOfBirth"/>
												<xsl:with-param name="title" select="'Date of Birth'"/>
											</xsl:call-template>
											<xsl:call-template name="writeText">
												<xsl:with-param name="objID" select="a:Person/a:Gender"/>
												<xsl:with-param name="title" select="'Gender'"/>
											</xsl:call-template>
											<fo:table-row>
												<fo:table-cell xsl:use-attribute-sets="table.header.properties">
													<fo:block>Identification Numbers</fo:block>
												</fo:table-cell>
												<fo:table-cell xsl:use-attribute-sets="table.body.properties">
													<fo:block>
														<xsl:for-each select="a:IDs">
															<xsl:if test="a:Type/a:Text">
																<xsl:value-of select="a:Type/a:Text" /> : 
															</xsl:if>
															<xsl:value-of select="a:ID" />
															<fo:block />
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
											<xsl:call-template name="writeAddress">
												<xsl:with-param name="objID" select="."/>
												<xsl:with-param name="title" select="'Address / Phone'"/>
											</xsl:call-template>
										</fo:table-body>
									</fo:table>
									<fo:block>
										<fo:leader />
									</fo:block>
								</xsl:if>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="writeMessage">
								<xsl:with-param name="title" select="'No Patient Demographics is found'"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Patient Demographics is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
				  
	<xsl:template name="displayAlerts">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Alerts'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Alerts">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Alerts/a:Alert">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Reaction</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:Reaction/a:Description/a:Text"/>
										<xsl:if test="a:Reaction/a:Severity/a:Text">
											<xsl:text>-</xsl:text>
											<xsl:value-of select="a:Reaction/a:Severity/a:Text"/>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Alert is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="displayAdvanceDirectives">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Advance Directives'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:AdvanceDirectives">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:AdvanceDirectives/a:AdvanceDirective">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Advance Directive is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displaySupportProviders">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Support Providers'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Support">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Support/a:SupportProvider">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:ActorRole"/>
								<xsl:with-param name="title" select="'Role'"/>
							</xsl:call-template>
							<xsl:call-template name="writeActorName">
								<xsl:with-param name="objID" select="a:ActorID"/>
								<xsl:with-param name="title" select="'Name'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Support Provider is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayFunctionalStatus">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Functional Status'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:FunctionalStatus">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:FunctionalStatus/a:Function">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Functional Status is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
		  
	<xsl:template name="displayProblems">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Problems'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Problems">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Problems/a:Problem">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Problems is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayProcedures">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Procedures'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Procedures">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Procedures/a:Procedure">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Location</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Locations/a:Location">
											<xsl:value-of select="a:Description/a:Text"/>
											<xsl:if test="a:Actor">
												<xsl:text>(</xsl:text>
												<xsl:call-template name="actorName">
													<xsl:with-param name="objID" select="a:Actor/a:ActorID"/>
												</xsl:call-template>
												<xsl:if test="a:Actor/a:ActorRole/a:Text">
													<xsl:text xml:space="preserve"> 
													</xsl:text>-<xsl:text xml:space="preserve"> 
													</xsl:text>
													<xsl:value-of select="a:ActorRole/a:Text"/>
													<xsl:text>)</xsl:text>
												</xsl:if>
											</xsl:if>
											<xsl:if test="position() != last()">
												<fo:block />
											</xsl:if>
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Substance"/>
								<xsl:with-param name="title" select="'Substance'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Method"/>
								<xsl:with-param name="title" select="'Method'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Position"/>
								<xsl:with-param name="title" select="'Position'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Site"/>
								<xsl:with-param name="title" select="'Site'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Procedure is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayMedications">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Medications'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Medications">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Medications/a:Medication">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Medication</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:Product/a:ProductName/a:Text"/>
										<xsl:if test="a:Product/a:BrandName">
											<xsl:text xml:space="preserve"> (</xsl:text>
											<xsl:value-of select="a:Product/a:BrandName/a:Text"/>
											<xsl:text>)</xsl:text>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Product/a:Form"/>
								<xsl:with-param name="title" select="'Form'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Strength</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Product/a:Strength">
											<xsl:if test="position() > 1">
											  <xsl:text>/</xsl:text>
											</xsl:if>
											<xsl:value-of select="a:Value"/>
											<xsl:text xml:space="preserve"> </xsl:text>
											<xsl:value-of select="a:Units/a:Unit"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Quantity</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:Quantity/a:Value"/>
										<xsl:text xml:space="preserve"> </xsl:text>
										<xsl:value-of select="a:Quantity/a:Units/a:Unit"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>SIG</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:apply-templates select="a:Directions"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Indications</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Indications/a:Indication">
											<xsl:call-template name="problemDescription">
												<xsl:with-param name="objID" select="a:InternalCCRLink/a:LinkID"/>
											</xsl:call-template>
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Instruction</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:PatientInstructions/a:Instruction">
											<xsl:value-of select="a:Text"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Refills</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Refills/a:Refill">
											<xsl:value-of select="a:Number"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Medication is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayImmunizations">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Immunizations'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Immunizations">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Immunizations/a:Immunization">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Product/a:ProductName"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Vaccine</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:Product/a:ProductName/a:Text"/>
										<xsl:if test="a:Product/a:Form">
										  <xsl:text xml:space="preserve"> (</xsl:text>
										  <xsl:value-of select="a:Product/a:Form/a:Text"/>
										  <xsl:text>)</xsl:text>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Directions/a:Direction/a:Route"/>
								<xsl:with-param name="title" select="'Route'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Directions/a:Direction/a:Site"/>
								<xsl:with-param name="title" select="'Site'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Immunization is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayVitalSigns">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Vital Signs'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:VitalSigns">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:VitalSigns/a:Result">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Vital Sign'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Result</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Test">
											<xsl:value-of select="a:Description/a:Text"/> - 
											<xsl:value-of select="a:TestResult/a:Value"/>
											<xsl:text xml:space="preserve"> </xsl:text> 
											<xsl:value-of select="a:TestResult/a:Units/a:Unit"/>
											<xsl:if test="a:Flag/a:Text">
												(<xsl:value-of select="a:Flag/a:Text"/>)
											</xsl:if>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Vital Sign is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayEncounters">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Encounters'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Encounters">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Encounters/a:Encounter">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Location</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Locations/a:Location">
											<xsl:value-of select="a:Description/a:Text"/>
											<xsl:call-template name="actorName">
												<xsl:with-param name="objID" select="a:Actor/a:ActorID"/>
											</xsl:call-template>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Practitioner</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Practitioners/a:Practitioner">
											<xsl:value-of select="a:Description/a:Text"/>
											<xsl:call-template name="actorName">
												<xsl:with-param name="objID" select="a:ActorID"/>
											</xsl:call-template>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Indications</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Indications/a:Indication">
											<xsl:call-template name="problemDescription">
												<xsl:with-param name="objID" select="a:InternalCCRLink/a:LinkID"/>
											</xsl:call-template>
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Encounter is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
				  
	<xsl:template name="displaySocialHistory">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Social History'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:SocialHistory">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:SocialHistory/a:SocialHistoryElement">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeCode">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Code'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Social History is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayFamilyHistory">
		<xsl:call-template name="writeTitle">
		<xsl:with-param name="title" select="'Family History'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:FamilyHistory">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:FamilyHistory/a:FamilyProblemHistory">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Code</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:apply-templates select="a:Problem/a:Description/a:Code"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Description</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Problem">
											<xsl:value-of select="a:Description/a:Text"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Relationship(s)</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:FamilyMember/a:ActorRole/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Family History is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

 	<xsl:template name="displayResultsDiscrete">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Results (Discrete)'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Results/a:Result[a:Test/a:TestResult/a:Value!='']">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Results/a:Result[a:Test/a:TestResult/a:Value!='']">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Test'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Result</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Test">
											<xsl:value-of select="a:Description/a:Text"/> - 
											<xsl:value-of select="a:TestResult/a:Value"/>
											<xsl:text xml:space="preserve"> </xsl:text> 
											<xsl:value-of select="a:TestResult/a:Units/a:Unit"/>
											<xsl:if test="a:Flag/a:Text">
												(<xsl:value-of select="a:Flag/a:Text"/>)
											</xsl:if>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Result (Discrete) is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
 	<xsl:template name="displayResultsReport">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Results (Report)'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Results/a:Result[a:Test/a:TestResult/a:Description/a:Text!='']">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Results/a:Result[a:Test/a:TestResult/a:Description/a:Text!='']">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Test'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Result</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:Test">
												<xsl:value-of select="a:Description/a:Text"/> - 
											<xsl:value-of select="a:TestResult/a:Description/a:Text"/>
											<xsl:if test="a:Flag/a:Text">
												(<xsl:value-of select="a:Flag/a:Text"/>)
											</xsl:if>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Result (Report) is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
 	<xsl:template name="displayInsurance">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Insurance'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:Payers">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:Payers/a:Payer">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Identification Numbers</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:IDs">
											<xsl:value-of select="a:Type/a:Text"/> : 
											<xsl:value-of select="a:ID"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Payment Provider</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:call-template name="actorName">
											<xsl:with-param name="objID" select="a:PaymentProvider/a:ActorID"/>
										</xsl:call-template>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Subscriber</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:call-template name="actorName">
											<xsl:with-param name="objID" select="a:Subscriber/a:ActorID"/>
										</xsl:call-template>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Insurance is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
	<xsl:template name="displayPlanOfCare">
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:PlanOfCare">
				<xsl:call-template name="displayPlanOfCareRecommendations" />
				<xsl:call-template name="displayPlanOfCareOrders" />
			</xsl:when>
			<xsl:otherwise>
				<fo:block xsl:use-attribute-sets="section.header.properties">Plan Of Care</fo:block>
					<fo:table xsl:use-attribute-sets="table.properties">
					<fo:table-column column-width="7in" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>No Plan Of Care is found</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

 	<xsl:template name="displayPlanOfCareRecommendations">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Plan Of Care Recommendations'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:PlanOfCare/a:Plan[a:Type/a:Text='Treatment Recommendation']">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:PlanOfCare/a:Plan[a:Type/a:Text='Treatment Recommendation']">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Recommendation</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:OrderRequest/a:Description/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Goal</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:OrderRequest/a:Goals/a:Goal/a:Description/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Status'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Plan Of Care Recommendation is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
 	<xsl:template name="displayPlanOfCareOrders">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Plan Of Care Orders'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:PlanOfCare/a:Plan[a:Type/a:Text='Order']">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:PlanOfCare/a:Plan[a:Type/a:Text='Order']">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Status"/>
								<xsl:with-param name="title" select="'Plan Status'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Type</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:OrderRequest/a:Procedures/a:Procedure/a:Type/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Procedure</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:apply-templates select="a:OrderRequest/a:Procedures/a:Procedure/a:Description/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Schedule</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:apply-templates select="a:OrderRequest/a:Procedures/a:Procedure/a:Interval/a:Value"/>
										<xsl:text xml:space="preserve"> </xsl:text>
										<xsl:value-of select="a:OrderRequest/a:Procedures/a:Procedure/a:Interval/a:Units/a:Unit"/>
										:
										<xsl:value-of select="a:OrderRequest/a:Procedures/a:Procedure/a:Duration/a:Value"/>
										<xsl:text xml:space="preserve"> </xsl:text>
										<xsl:value-of select="a:OrderRequest/a:Procedures/a:Procedure/a:Duration/a:Units/a:Unit"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Location</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:for-each select="a:OrderRequest/a:Procedures/a:Procedure/a:Locations">
											<xsl:value-of select="a:Location/a:Description/a:Text"/>
											<fo:block />
										</xsl:for-each>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:OrderRequest/a:Procedures/a:Procedure/a:Substance"/>
								<xsl:with-param name="title" select="'Substance'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:OrderRequest/a:Procedures/a:Procedure/a:Method"/>
								<xsl:with-param name="title" select="'Method'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:OrderRequest/a:Procedures/a:Procedure/a:Position"/>
								<xsl:with-param name="title" select="'Position'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:OrderRequest/a:Procedures/a:Procedure/a:Site"/>
								<xsl:with-param name="title" select="'Site'"/>
							</xsl:call-template>
							<xsl:call-template name="writeSource">
								<xsl:with-param name="objID" select="a:Source"/>
								<xsl:with-param name="title" select="'Source'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Plan Of Care Order is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
 
 	<xsl:template name="displayHealthCareProviders">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Health Care Providers'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Body/a:HealthCareProviders">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Body/a:HealthCareProviders/a:Provider">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Role</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:value-of select="a:ActorRole/a:Text"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="table.header.properties">
									<fo:block>Name</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="table.body.properties">
									<fo:block>
										<xsl:call-template name="actorName">
											<xsl:with-param name="objID" select="a:ActorID"/>
										</xsl:call-template>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Health Care Provider is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
 	<xsl:template name="displayReferences">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'References'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:References">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:References/a:Reference">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Type"/>
								<xsl:with-param name="title" select="'Type'"/>
							</xsl:call-template>
							<xsl:call-template name="writeDate">
								<xsl:with-param name="objID" select="."/>
								<xsl:with-param name="title" select="'Date'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Description"/>
								<xsl:with-param name="title" select="'Description'"/>
							</xsl:call-template>
							<xsl:call-template name="writeText">
								<xsl:with-param name="objID" select="a:Locations/a:Location/a:Description"/>
								<xsl:with-param name="title" select="'Location'"/>
							</xsl:call-template>
						</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Reference is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

 	<xsl:template name="displayAdditionalInformationPeople">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Additional Information About People'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Actors/a:Actor[a:Person]">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Actors/a:Actor">
					<xsl:sort data-type="text" order="ascending" select="a:Person/a:Name/a:DisplayName|a:Person/a:Name/a:CurrentName/a:Family"/>
					<xsl:if test="a:Person">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
								<xsl:call-template name="writeActorName">
									<xsl:with-param name="objID" select="a:ActorObjectID"/>
									<xsl:with-param name="title" select="'Name'"/>
								</xsl:call-template>
								<xsl:call-template name="writeText">
									<xsl:with-param name="objID" select="a:Specialty"/>
									<xsl:with-param name="title" select="'Specialty'"/>
								</xsl:call-template>
								<xsl:call-template name="writeText">
									<xsl:with-param name="objID" select="a:Relation"/>
									<xsl:with-param name="title" select="'Relation'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Identification</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:IDs">
												<xsl:value-of select="a:Type/a:Text"/> : 
												<xsl:value-of select="a:ID"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:call-template name="writeAddress">
									<xsl:with-param name="objID" select="."/>
									<xsl:with-param name="title" select="'Address/Phone'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>E-mail</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:EMail">
												<xsl:value-of select="a:Value"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						<fo:block>
							<fo:leader />
						</fo:block>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Additional Information About People is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
				  
 	<xsl:template name="displayAdditionalInformationOrganization">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Additional Information About Organizations'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Actors/a:Actor[a:Organization]">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Actors/a:Actor">
				<xsl:sort data-type="text" order="ascending" select="a:Organization/a:Name"/>
					<xsl:if test="a:Organization">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Name</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
										   <xsl:value-of select="a:Organization/a:Name"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:call-template name="writeText">
									<xsl:with-param name="objID" select="a:Specialty"/>
									<xsl:with-param name="title" select="'Specialty'"/>
								</xsl:call-template>
								<xsl:call-template name="writeText">
									<xsl:with-param name="objID" select="a:Relation"/>
									<xsl:with-param name="title" select="'Relation'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Identification Numbers</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:IDs">
												<xsl:value-of select="a:Type/a:Text"/> : 
												<xsl:value-of select="a:ID"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:call-template name="writeAddress">
									<xsl:with-param name="objID" select="."/>
									<xsl:with-param name="title" select="'Address/Phone'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>E-mail</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:EMail">
												<xsl:value-of select="a:Value"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						<fo:block>
							<fo:leader />
						</fo:block>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Additional Information About Organization is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

 	<xsl:template name="displayInformationSystem">
		<xsl:call-template name="writeTitle">
			<xsl:with-param name="title" select="'Information Systems'"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="a:ContinuityOfCareRecord/a:Actors/a:Actor[a:InformationSystem]">
				<xsl:for-each select="a:ContinuityOfCareRecord/a:Actors/a:Actor">
				<xsl:sort data-type="text" order="ascending" select="a:InformationSystem/a:Name"/>
					<xsl:if test="a:InformationSystem">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
								<xsl:call-template name="writeActorName">
									<xsl:with-param name="objID" select="a:ActorObjectID"/>
									<xsl:with-param name="title" select="'Name'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Type</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:value-of select="a:InformationSystem/a:Type"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Version</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:value-of select="a:InformationSystem/a:Version"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>Identification Numbers</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:IDs">
												<xsl:value-of select="a:Type/a:Text"/> : 
												<xsl:value-of select="a:ID"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:call-template name="writeAddress">
									<xsl:with-param name="objID" select="."/>
									<xsl:with-param name="title" select="'Address/Phone'"/>
								</xsl:call-template>
								<fo:table-row>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block>E-mail</fo:block>
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block>
											<xsl:for-each select="a:EMail">
												<xsl:value-of select="a:Value"/>
												<fo:block />
											</xsl:for-each>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						<fo:block>
							<fo:leader />
						</fo:block>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="writeMessage">
					<xsl:with-param name="title" select="'No Additional Information About Information System is found'"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
