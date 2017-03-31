<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:n1="urn:hl7-org:v3">

<xsl:attribute-set name="page.number.properties">
	<xsl:attribute name="text-align">end</xsl:attribute>
	<xsl:attribute name="font-size">8pt</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="h3.properties">
	<xsl:attribute name="text-align">start</xsl:attribute>
	<xsl:attribute name="font-size">10pt</xsl:attribute>
	<xsl:attribute name="font-weight">bold</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="h4.properties">
	<xsl:attribute name="text-align">start</xsl:attribute>
	<xsl:attribute name="font-size">9pt</xsl:attribute>
	<xsl:attribute name="font-weight">bold</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="table.properties">
	<xsl:attribute name="table-layout">fixed</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="b.properties">
	<xsl:attribute name="padding">1pt</xsl:attribute>
	<xsl:attribute name="text-align">start</xsl:attribute>
	<xsl:attribute name="font-size">8pt</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="p.properties">
    <xsl:attribute name="padding">1pt</xsl:attribute>
    <xsl:attribute name="text-align">start</xsl:attribute>
    <xsl:attribute name="font-size">8pt</xsl:attribute>
    <xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="th.properties">
	<xsl:attribute name="background-color">#E3E3E3</xsl:attribute>
	<xsl:attribute name="padding">1pt</xsl:attribute>
	<xsl:attribute name="border-style">solid</xsl:attribute>
	<xsl:attribute name="border-width">1pt</xsl:attribute>
	<xsl:attribute name="border-color">black</xsl:attribute>
	<xsl:attribute name="text-align">start</xsl:attribute>
	<xsl:attribute name="font-size">8pt</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="td.properties">
	<xsl:attribute name="padding">1pt</xsl:attribute>
	<xsl:attribute name="border-style">solid</xsl:attribute>
	<xsl:attribute name="border-width">1pt</xsl:attribute>
	<xsl:attribute name="border-color">black</xsl:attribute>
	<xsl:attribute name="text-align">start</xsl:attribute>
	<xsl:attribute name="font-size">8pt</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="h1center.properties">
	<xsl:attribute name="text-align">center</xsl:attribute>
	<xsl:attribute name="font-size">12pt</xsl:attribute>
	<xsl:attribute name="font-weight">bold</xsl:attribute>
	<xsl:attribute name="font-family">sans-serif</xsl:attribute>
</xsl:attribute-set>

<xsl:output indent="yes" />

<xsl:template match="n1:ClinicalDocument">
	<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
		<xsl:call-template name="PageMaster" />
		<fo:page-sequence master-reference="simple">
			<fo:static-content flow-name="xsl-region-before">
				<fo:block xsl:use-attribute-sets="page.number.properties">
					Page: <fo:page-number />
				</fo:block>
			</fo:static-content>

			<fo:flow flow-name="xsl-region-body">
				<fo:block xsl:use-attribute-sets="h1center.properties">
					<xsl:choose>
						<xsl:when test="n1:title">
							<xsl:value-of select="n1:title" />
						</xsl:when>
						<xsl:otherwise>
							Clinical Document
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
				<xsl:call-template name="emptyLine"/>
				
				<!-- Process all templates under ClinicalDocument -->
				<xsl:call-template name="recordTarget"/>
				<xsl:call-template name="documentGeneral"/>
				<xsl:call-template name="documentationOf"/>  
				<xsl:call-template name="author"/>
				<xsl:apply-templates select="n1:component/n1:structuredBody"/>
				<xsl:call-template name="solidLine"/>
				<xsl:call-template name="emptyLine"/>
				<xsl:call-template name="componentof"/>
				<xsl:call-template name="custodian"/>
				<xsl:call-template name="participant"/>
				<xsl:call-template name="dataEnterer"/>
				<xsl:call-template name="authenticator"/>
				<xsl:call-template name="informant"/>
				<xsl:call-template name="informationRecipient"/>
				<xsl:call-template name="legalAuthenticator"/>
			</fo:flow>
		</fo:page-sequence>
	</fo:root>
</xsl:template>

<xsl:template name="PageMaster">
	<!-- defines page layout -->
	<fo:layout-master-set>
		<fo:simple-page-master master-name="simple"
			page-height="11in" page-width="8.5in" margin-top="0.5in"
			margin-bottom="0.5in" margin-left="0.5in" margin-right="0.5in">
			<fo:region-body margin-top="0.5in" />
			<fo:region-before extent="0.5in" />
			<fo:region-after extent="0.5in" />
		</fo:simple-page-master>
	</fo:layout-master-set>
</xsl:template>

<xsl:template name="emptyLine">
	<fo:block>
		<fo:leader />
	</fo:block>
</xsl:template>

<xsl:template name="solidLine">
	<fo:block>
		<fo:leader leader-pattern="rule" leader-length="100%" color="black"/>
	</fo:block>
</xsl:template>

<!-- recordTarget -->
<xsl:template name="recordTarget">
	<fo:table  xsl:use-attribute-sets="table.properties">
		<fo:table-column column-width="20%"/>
		<fo:table-column column-width="30%" />
		<fo:table-column column-width="20%" />
		<fo:table-column column-width="30%" />
		<fo:table-body>
			<xsl:for-each select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole">
				<xsl:if test="not(n1:id/@nullFlavor)">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Patient</fo:block>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-name">
									<xsl:with-param name="name" select="n1:patient/n1:name"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Date of birth</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-time">
									<xsl:with-param name="datetime" select="n1:patient/n1:birthTime"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Sex</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:for-each select="n1:patient/n1:administrativeGenderCode">
									<xsl:call-template name="show-gender"/>
								</xsl:for-each>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Contact info</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-contactInfo">
									<xsl:with-param name="contact" select="."/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Patient IDs</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:for-each select="n1:id">
									<xsl:call-template name="show-id"/>
									<fo:block />
								</xsl:for-each>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</xsl:for-each>
		</fo:table-body>
	</fo:table>
	<xsl:call-template name="emptyLine"/>
</xsl:template>

<!-- documentGeneral elements -->
<xsl:template name="documentGeneral">
	<fo:table  xsl:use-attribute-sets="table.properties">
		<fo:table-column column-width="20%"/>
		<fo:table-column column-width="80%" />
		<fo:table-body>
			<fo:table-row>
				<fo:table-cell xsl:use-attribute-sets="th.properties">
					<fo:block>Document Id</fo:block>
				</fo:table-cell>
				<fo:table-cell xsl:use-attribute-sets="td.properties">
					<fo:block>
						<xsl:call-template name="show-id">
							<xsl:with-param name="id" select="n1:id"/>
						</xsl:call-template>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell xsl:use-attribute-sets="th.properties">
					<fo:block>Document Created:</fo:block>
				</fo:table-cell>
				<fo:table-cell xsl:use-attribute-sets="td.properties">
					<fo:block>
						<xsl:call-template name="show-time">
							<xsl:with-param name="datetime" select="n1:effectiveTime"/>
						</xsl:call-template>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</fo:table-body>
	</fo:table>
	<xsl:call-template name="emptyLine"/>
</xsl:template>
 
<!-- documentationOf -->
<xsl:template name="documentationOf">
	<xsl:if test="n1:documentationOf and n1:documentationOf/n1:serviceEvent/n1:performer" >
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:documentationOf">
					<xsl:if test="n1:serviceEvent/@classCode and n1:serviceEvent/n1:code">
						<xsl:variable name="displayName">
							<xsl:call-template name="show-actClassCode">
								<xsl:with-param name="clsCode" select="n1:serviceEvent/@classCode"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:if test="$displayName">
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="th.properties">
									<fo:block>
										<xsl:call-template name="firstCharCaseUp">
											<xsl:with-param name="data" select="$displayName"/>
										</xsl:call-template>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="td.properties">
									<fo:block>
										<xsl:call-template name="show-code">
											<xsl:with-param name="code" select="n1:serviceEvent/n1:code"/>
										</xsl:call-template>
										<xsl:if test="n1:serviceEvent/n1:effectiveTime">
											<xsl:choose>
												<xsl:when test="n1:serviceEvent/n1:effectiveTime/@value">
													<xsl:text>&#160;at&#160;</xsl:text>
													<xsl:call-template name="show-time">
														<xsl:with-param name="datetime" select="n1:serviceEvent/n1:effectiveTime"/>
													</xsl:call-template>
												</xsl:when>
												<xsl:when test="n1:serviceEvent/n1:effectiveTime/n1:low">
													<xsl:text>&#160;from&#160;</xsl:text>
													<xsl:call-template name="show-time">
														<xsl:with-param name="datetime" select="n1:serviceEvent/n1:effectiveTime/n1:low"/>
													</xsl:call-template>
													<xsl:if test="n1:serviceEvent/n1:effectiveTime/n1:high">
														<xsl:text> to </xsl:text>
														<xsl:call-template name="show-time">
															<xsl:with-param name="datetime" select="n1:serviceEvent/n1:effectiveTime/n1:high"/>
														</xsl:call-template>
													</xsl:if>
												</xsl:when>
											</xsl:choose>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:if>
					</xsl:if>
					<xsl:for-each select="n1:serviceEvent/n1:performer">
						<xsl:variable name="displayName">
							<xsl:call-template name="show-participationType">
								<xsl:with-param name="ptype" select="@typeCode"/>
							</xsl:call-template>
							<xsl:text> </xsl:text>
							<xsl:if test="n1:functionCode/@code">
								<xsl:text>(</xsl:text>
									<xsl:call-template name="show-participationFunction">
										<xsl:with-param name="pFunction" select="n1:functionCode/@code"/>
								</xsl:call-template>
								<xsl:text>)</xsl:text>
							</xsl:if>
						</xsl:variable>
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>
									<xsl:call-template name="firstCharCaseUp">
										<xsl:with-param name="data" select="$displayName"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-assignedEntity">
										<xsl:with-param name="asgnEntity" select="n1:assignedEntity"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- author -->
<xsl:template name="author">
	<fo:table  xsl:use-attribute-sets="table.properties">
		<fo:table-column column-width="20%"/>
		<fo:table-column column-width="80%" />
		<fo:table-body>
			<xsl:for-each select="n1:author/n1:assignedAuthor">
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="th.properties">
						<fo:block>Author</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.properties">
						<fo:block>
							<xsl:choose>
								<xsl:when test="n1:assignedPerson/n1:name">
									<xsl:call-template name="show-name">
										<xsl:with-param name="name" select="n1:assignedPerson/n1:name"/>
									</xsl:call-template>
									<xsl:if test="n1:representedOrganization">
										<xsl:text>, </xsl:text>
										<xsl:call-template name="show-name">
											<xsl:with-param name="name" select="n1:representedOrganization/n1:name"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:when>
								<xsl:when test="n1:assignedAuthoringDevice/n1:softwareName">
									<xsl:value-of select="n1:assignedAuthoringDevice/n1:softwareName"/>
								</xsl:when>
								<xsl:when test="n1:representedOrganization">
									<xsl:call-template name="show-name">
										<xsl:with-param name="name" select="n1:representedOrganization/n1:name"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:for-each select="n1:id">
										<xsl:call-template name="show-id"/>
										<fo:block />
									</xsl:for-each>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<xsl:if test="n1:addr | n1:telecom">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Contact info</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-contactInfo">
									<xsl:with-param name="contact" select="."/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</xsl:for-each>
		</fo:table-body>
	</fo:table>
	<xsl:call-template name="emptyLine"/>
</xsl:template>

<!-- componentOf -->
<xsl:template name="componentof">
	<xsl:if test="n1:componentOf">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:componentOf/n1:encompassingEncounter">
					<xsl:if test="n1:location/n1:healthCareFacility">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Encounter Location</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:choose>
										<xsl:when test="n1:location/n1:healthCareFacility/n1:location/n1:name">
											<xsl:call-template name="show-name">
												<xsl:with-param name="name" select="n1:location/n1:healthCareFacility/n1:location/n1:name"/>
											</xsl:call-template>
											<xsl:for-each select="n1:location/n1:healthCareFacility/n1:serviceProviderOrganization/n1:name">
												<xsl:text> of </xsl:text>
												<xsl:call-template name="show-name">
													<xsl:with-param name="name" select="n1:location/n1:healthCareFacility/n1:serviceProviderOrganization/n1:name"/>
												</xsl:call-template>
											</xsl:for-each>
										</xsl:when>
										<xsl:when test="n1:location/n1:healthCareFacility/n1:code">
											<xsl:call-template name="show-code">
												<xsl:with-param name="code" select="n1:location/n1:healthCareFacility/n1:code"/>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<xsl:if test="n1:location/n1:healthCareFacility/n1:id">
												<xsl:text>id: </xsl:text>
												<xsl:for-each select="n1:location/n1:healthCareFacility/n1:id">
													<xsl:call-template name="show-id">
														<xsl:with-param name="id" select="."/>
													</xsl:call-template>
												</xsl:for-each>
											</xsl:if>
										</xsl:otherwise>
									</xsl:choose>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
					<xsl:if test="n1:responsibleParty">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Responsible party</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-assignedEntity">
										<xsl:with-param name="asgnEntity" select="n1:responsibleParty/n1:assignedEntity"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
					<xsl:if test="n1:responsibleParty/n1:assignedEntity/n1:addr | n1:responsibleParty/n1:assignedEntity/n1:telecom">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Contact info</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="n1:responsibleParty/n1:assignedEntity"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- custodian -->
<xsl:template name="custodian">
	<fo:table  xsl:use-attribute-sets="table.properties">
		<fo:table-column column-width="20%"/>
		<fo:table-column column-width="80%" />
		<fo:table-body>
			<fo:table-row>
				<fo:table-cell xsl:use-attribute-sets="th.properties">
					<fo:block>Document maintained by</fo:block>
				</fo:table-cell>
				<fo:table-cell xsl:use-attribute-sets="td.properties">
					<fo:block>
						<xsl:choose>
							<xsl:when test="n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization/n1:name">
								<xsl:call-template name="show-name">
									<xsl:with-param name="name" select="n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization/n1:name"/>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each select="n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization/n1:id">
								<xsl:call-template name="show-id"/>
									<xsl:if test="position()!=last()">
										<fo:block />
									</xsl:if>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<xsl:if test="n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization/n1:addr | n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization/n1:telecom">
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="th.properties">
						<fo:block>Contact info</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.properties">
						<fo:block>
							<xsl:call-template name="show-contactInfo">
								<xsl:with-param name="contact" select="n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization"/>
							</xsl:call-template>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</xsl:if>
		</fo:table-body>
	</fo:table>
	<xsl:call-template name="emptyLine"/>
</xsl:template>

<!-- participant -->
<xsl:template name="participant">
	<xsl:if test="n1:participant">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:participant">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>
								<xsl:variable name="participtRole">
									<xsl:call-template name="translateRoleAssoCode">
										<xsl:with-param name="code" select="n1:associatedEntity/@classCode"/>
									</xsl:call-template>
								</xsl:variable>
								<xsl:choose>
									<xsl:when test="$participtRole">
										<xsl:call-template name="firstCharCaseUp">
											<xsl:with-param name="data" select="$participtRole"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>Participant</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:if test="n1:functionCode">
									<xsl:call-template name="show-code">
										<xsl:with-param name="code" select="n1:functionCode"/>
									</xsl:call-template>
								</xsl:if>
								<xsl:call-template name="show-associatedEntity">
									<xsl:with-param name="assoEntity" select="n1:associatedEntity"/>
								</xsl:call-template>
								<xsl:if test="n1:time">
									<xsl:if test="n1:time/n1:low">
										<xsl:text> from </xsl:text>
										<xsl:call-template name="show-time">
											<xsl:with-param name="datetime" select="n1:time/n1:low"/>
										</xsl:call-template>
									</xsl:if>
									<xsl:if test="n1:time/n1:high">
										<xsl:text> to </xsl:text>
										<xsl:call-template name="show-time">
											<xsl:with-param name="datetime" select="n1:time/n1:high"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:if>
								<xsl:if test="position() != last()">
									<fo:block />
								</xsl:if>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test="n1:associatedEntity/n1:addr | n1:associatedEntity/n1:telecom">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Contact info</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="n1:associatedEntity"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- dataEnterer -->
<xsl:template name="dataEnterer">
	<xsl:if test="n1:dataEnterer">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="th.properties">
						<fo:block>Entered by</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.properties">
						<fo:block>
							<xsl:call-template name="show-assignedEntity">
								<xsl:with-param name="asgnEntity" select="n1:dataEnterer/n1:assignedEntity"/>
							</xsl:call-template>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<xsl:if test="n1:dataEnterer/n1:assignedEntity/n1:addr | n1:dataEnterer/n1:assignedEntity/n1:telecom">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Contact info</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-contactInfo">
									<xsl:with-param name="contact" select="n1:dataEnterer/n1:assignedEntity"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!--  authenticator -->
<xsl:template name="authenticator">
	<xsl:if test="n1:authenticator">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:authenticator">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Signed</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-name">
									<xsl:with-param name="name" select="n1:assignedEntity/n1:assignedPerson/n1:name"/>
								</xsl:call-template>
								<xsl:text> at </xsl:text>
								<xsl:call-template name="show-time">
									<xsl:with-param name="date" select="n1:time"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test="n1:assignedEntity/n1:addr | n1:assignedEntity/n1:telecom">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Contact info</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="n1:assignedEntity"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- informant -->
<xsl:template name="informant">
	<xsl:if test="n1:informant">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:informant">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Informant</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:if test="n1:assignedEntity">
									<xsl:call-template name="show-assignedEntity">
										<xsl:with-param name="asgnEntity" select="n1:assignedEntity"/>
									</xsl:call-template>
								</xsl:if>
								<xsl:if test="n1:relatedEntity">
									<xsl:call-template name="show-relatedEntity">
										<xsl:with-param name="relatedEntity" select="n1:relatedEntity"/>
									</xsl:call-template>
								</xsl:if>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:choose>
						<xsl:when test="n1:assignedEntity/n1:addr | n1:assignedEntity/n1:telecom">
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="th.properties">
									<fo:block>Contact info</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="td.properties">
									<fo:block>
										<xsl:if test="n1:assignedEntity">
											<xsl:call-template name="show-contactInfo">
											<xsl:with-param name="contact" select="n1:assignedEntity"/>
											</xsl:call-template>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:when>
						<xsl:when test="n1:relatedEntity/n1:addr | n1:relatedEntity/n1:telecom">
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="th.properties">
									<fo:block>Contact info</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="td.properties">
									<fo:block>
										<xsl:if test="n1:relatedEntity">
											<xsl:call-template name="show-contactInfo">
												<xsl:with-param name="contact" select="n1:relatedEntity"/>
											</xsl:call-template>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- informantionRecipient -->
<xsl:template name="informationRecipient">
	<xsl:if test="n1:informationRecipient">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<xsl:for-each select="n1:informationRecipient">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Information recipient:</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:choose>
									<xsl:when test="n1:intendedRecipient/n1:informationRecipient/n1:name">
										<xsl:for-each select="n1:intendedRecipient/n1:informationRecipient">
											<xsl:call-template name="show-name">
												<xsl:with-param name="name" select="n1:name"/>
											</xsl:call-template>
											<xsl:if test="position() != last()">
												<fo:block />
											</xsl:if>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<xsl:for-each select="n1:intendedRecipient">
											<xsl:for-each select="n1:id">
												<xsl:call-template name="show-id"/>
											</xsl:for-each>
											<xsl:if test="position() != last()">
												<fo:block />
											</xsl:if>
											<fo:block />
										</xsl:for-each>
									</xsl:otherwise>
								</xsl:choose>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test="n1:intendedRecipient/n1:addr | n1:intendedRecipient/n1:telecom">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="th.properties">
								<fo:block>Contact info</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="td.properties">
								<fo:block>
									<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="n1:intendedRecipient"/>
									</xsl:call-template>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</xsl:for-each>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- legalAuthenticator -->
<xsl:template name="legalAuthenticator">
	<xsl:if test="n1:legalAuthenticator">
		<fo:table  xsl:use-attribute-sets="table.properties">
			<fo:table-column column-width="20%"/>
			<fo:table-column column-width="80%" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="th.properties">
						<fo:block>Legal authenticator</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.properties">
						<fo:block>
							<xsl:call-template name="show-assignedEntity">
								<xsl:with-param name="asgnEntity" select="n1:legalAuthenticator/n1:assignedEntity"/>
							</xsl:call-template>
							<xsl:text> </xsl:text>
							<xsl:call-template name="show-sig">
								<xsl:with-param name="sig" select="n1:legalAuthenticator/n1:signatureCode"/>
							</xsl:call-template>
							<xsl:if test="n1:legalAuthenticator/n1:time/@value">
								<xsl:text> at </xsl:text>
								<xsl:call-template name="show-time">
								<xsl:with-param name="datetime" select="n1:legalAuthenticator/n1:time"/>
								</xsl:call-template>
							</xsl:if>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<xsl:if test="n1:legalAuthenticator/n1:assignedEntity/n1:addr | n1:legalAuthenticator/n1:assignedEntity/n1:telecom">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="th.properties">
							<fo:block>Contact info</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="td.properties">
							<fo:block>
								<xsl:call-template name="show-contactInfo">
									<xsl:with-param name="contact" select="n1:legalAuthenticator/n1:assignedEntity"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
			</fo:table-body>
		</fo:table>
		<xsl:call-template name="emptyLine"/>
	</xsl:if>
</xsl:template>

<!-- show StructuredBody  -->
<xsl:template match="n1:component/n1:structuredBody">
	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="section" />
		<fo:block>
			<fo:leader />
		</fo:block>
	</xsl:for-each>
</xsl:template>
 
 <!-- top level component/section: display title and text,
     and process any nested component/sections
  -->
<xsl:template name="section">
	<xsl:call-template name="section-title">
		<xsl:with-param name="title" select="n1:title"/>
	</xsl:call-template>
	<xsl:call-template name="section-author"/>
	<xsl:call-template name="section-text"/>
	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="nestedSection">
			<xsl:with-param name="margin" select="2"/>
		</xsl:call-template>
	</xsl:for-each>
</xsl:template>

<!-- top level section title -->
<xsl:template name="section-title">
	<xsl:param name="title"/>
	<fo:block xsl:use-attribute-sets="h3.properties">
		<xsl:value-of select="$title"/>
	</fo:block>
</xsl:template>

<!-- section author -->
<xsl:template name="section-author">
	<xsl:if test="count(n1:author)&gt;0">
		<fo:block xsl:use-attribute-sets="b.properties">
			<xsl:text>Section Author: </xsl:text>
		</fo:block>
		<xsl:for-each select="n1:author/n1:assignedAuthor">
			<xsl:choose>
				<xsl:when test="n1:assignedPerson/n1:name">
					<fo:block xsl:use-attribute-sets="b.properties">
						<xsl:call-template name="show-name">
							<xsl:with-param name="name" select="n1:assignedPerson/n1:name"/>
						</xsl:call-template>
						<xsl:if test="n1:representedOrganization">
							<xsl:text>, </xsl:text>
							<xsl:call-template name="show-name">
								<xsl:with-param name="name" select="n1:representedOrganization/n1:name"/>
							</xsl:call-template>
						</xsl:if>
					</fo:block>
				</xsl:when>
				<xsl:when test="n1:assignedAuthoringDevice/n1:softwareName">
					<fo:block xsl:use-attribute-sets="b.properties">
						<xsl:value-of select="n1:assignedAuthoringDevice/n1:softwareName"/>
					</fo:block>
				</xsl:when>
				<xsl:otherwise>
					<xsl:for-each select="n1:id">
						<xsl:call-template name="show-id"/>
						<fo:block />
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	  	<fo:block />
	</xsl:if>
</xsl:template>
 
<!-- top-level section Text   -->
<xsl:template name="section-text">
	<fo:block xsl:use-attribute-sets="b.properties">
		<xsl:apply-templates select="n1:text" />
	</fo:block>
</xsl:template>

<!-- nested component/section -->
<xsl:template name="nestedSection">
	<xsl:param name="margin" />
	<fo:block  xsl:use-attribute-sets="h4.properties" margin="{$margin}em;">
		<xsl:value-of select="n1:title"/>
	</fo:block>
	<fo:block  xsl:use-attribute-sets="h4.properties" margin="{$margin}em;">
		<xsl:apply-templates select="n1:text"/>
	</fo:block>
	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="nestedSection">
			<xsl:with-param name="margin" select="2*$margin"/>
		</xsl:call-template>
	</xsl:for-each>
</xsl:template>

<!-- render paragraph -->
<xsl:template match="n1:paragraph">
	<fo:block xsl:use-attribute-sets="b.properties">
		<xsl:apply-templates/>
	</fo:block>
	<fo:block />
</xsl:template>


<!--   pre format  -->
<xsl:template match="n1:pre">
	<fo:block  xsl:use-attribute-sets="td.properties">
		<xsl:apply-templates/>
	</fo:block>
</xsl:template>
 
 <!--   Content w/ deleted text is hidden -->
 <xsl:template match="n1:content[@revised='delete']" />
 
<!--   content  -->
<xsl:template match="n1:content">
	<xsl:apply-templates/>
</xsl:template>

 <!-- line break -->
 <xsl:template match="n1:br">
	<xsl:apply-templates/>
	<fo:block/>
 </xsl:template>
 
<!--   list  -->
<xsl:template match="n1:list">
	<xsl:if test="n1:caption">
		<fo:block  xsl:use-attribute-sets="p.properties">
			<xsl:apply-templates select="n1:caption"/>
		</fo:block>
	</xsl:if>
	<xsl:for-each select="n1:item">
		<fo:block>
			<xsl:apply-templates/>
		</fo:block>
	</xsl:for-each>
</xsl:template>

<xsl:template match="n1:list[@listType='ordered']">
	<xsl:if test="n1:caption">
		<fo:block  xsl:use-attribute-sets="b.properties">
			<xsl:apply-templates select="n1:caption"/>
		</fo:block>
	</xsl:if>
	<xsl:for-each select="n1:item">
		<fo:block>
			<xsl:apply-templates/>
		</fo:block>
	</xsl:for-each>
</xsl:template>

<!--   caption  -->
<xsl:template match="n1:caption">
	<xsl:apply-templates/>
	<xsl:text>: </xsl:text>
</xsl:template>

<!--  Tables   -->
<xsl:template match="n1:table">
	<fo:table  xsl:use-attribute-sets="table.properties">
		<xsl:variable name="ds" select="n1:thead" />
		<xsl:for-each select="$ds/n1:tr/n1:th">
			<fo:table-column column-width="proportional-column-width(1)" />
		</xsl:for-each>
		<fo:table-body>
			<xsl:apply-templates/>
		</fo:table-body>
	</fo:table>
</xsl:template>

<xsl:template match="n1:thead">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="n1:tfoot">
	<xsl:apply-templates/>
</xsl:template>
	
<xsl:template match="n1:tbody">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="n1:colgroup">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="n1:col">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="n1:tr">
	<fo:table-row >
		<xsl:apply-templates/>
	</fo:table-row >
</xsl:template>

<xsl:template match="n1:th">
	<fo:table-cell xsl:use-attribute-sets="th.properties">
		<fo:block>
			<xsl:apply-templates/>
		</fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template match="n1:td">
	<fo:table-cell xsl:use-attribute-sets="td.properties">
		<xsl:if test="@colspan">
			<xsl:attribute name="number-columns-spanned">
				<xsl:value-of select="@colspan"/>
			</xsl:attribute>
		</xsl:if>			
		<fo:block>
			<xsl:call-template name="zero_width_space_1">
				<xsl:with-param name="data" select="."/>
			</xsl:call-template> 			
		</fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template match="n1:table/n1:caption">
	<fo:block  xsl:use-attribute-sets="b.properties">
		<xsl:apply-templates/>
	</fo:block>
</xsl:template>
 
<!--   RenderMultiMedia 
this currently only handles GIF's and JPEG's.  It could, however,
be extended by including other image MIME types in the predicate
and/or by generating <object> or <applet> tag with the correct
params depending on the media type  @ID  =$imageRef  referencedObject
-->
<xsl:template match="n1:renderMultiMedia">
	<xsl:variable name="imageRef" select="@referencedObject"/>
	<xsl:choose>
		<xsl:when test="//n1:regionOfInterest[@ID=$imageRef]">
			<!-- Here is where the Region of Interest image referencing goes -->
			<xsl:if test="//n1:regionOfInterest[@ID=$imageRef]//n1:observationMedia/n1:value[@mediaType='image/gif' or @mediaType='image/jpeg']">
				<fo:block>
					<fo:external-graphic>
						<xsl:attribute name="src">
							<xsl:value-of select="//n1:regionOfInterest[@ID=$imageRef]//n1:observationMedia/n1:value/n1:reference/@value"/>
						</xsl:attribute>
					</fo:external-graphic>
				</fo:block>				
				<xsl:element name="img">
					<xsl:attribute name="src">
					<xsl:value-of select="//n1:regionOfInterest[@ID=$imageRef]//n1:observationMedia/n1:value/n1:reference/@value"/></xsl:attribute>
				</xsl:element>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<!-- Here is where the direct MultiMedia image referencing goes -->
			<xsl:if test="//n1:observationMedia[@ID=$imageRef]/n1:value[@mediaType='image/gif' or @mediaType='image/jpeg']">
				<fo:block>
					<fo:external-graphic>
						<xsl:attribute name="src">
							<xsl:value-of select="//n1:observationMedia[@ID=$imageRef]/n1:value/n1:reference/@value"/>
						</xsl:attribute>
					</fo:external-graphic>
				</fo:block>				
			</xsl:if>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--    Superscript or Subscript   -->
<xsl:template match="n1:sup">
	<xsl:apply-templates/>
</xsl:template>
	
<xsl:template match="n1:sub">
	<xsl:apply-templates/>
</xsl:template>
 
<!-- show nonXMLBody -->
<xsl:template match='n1:component/n1:nonXMLBody'>
	<xsl:choose>
	<xsl:when test='n1:text/@mediaType="text/plain"'>
		<fo:block>
			<xsl:value-of select='n1:text/text()'/>
		</fo:block>
	</xsl:when>
	<xsl:otherwise>
		<fo:block>
			Cannot display the text
		</fo:block>
	</xsl:otherwise>
	</xsl:choose>
</xsl:template>
 
<!-- show-signature -->
<xsl:template name="show-sig">
	<xsl:param name="sig"/>
	<xsl:choose>
		<xsl:when test="$sig/@code =&apos;S&apos;">
			<xsl:text>signed</xsl:text>
		</xsl:when>
		<xsl:when test="$sig/@code=&apos;I&apos;">
			<xsl:text>intended</xsl:text>
		</xsl:when>
		<xsl:when test="$sig/@code=&apos;X&apos;">
			<xsl:text>signature required</xsl:text>
		</xsl:when>
	</xsl:choose>
</xsl:template>
 
<!-- show-id -->
<xsl:template name="show-id">
	<xsl:param name="id"/>
	<xsl:choose>
		<xsl:when test="not($id)">
			<xsl:if test="not(@nullFlavor)">
				<xsl:if test="@extension">
					<xsl:value-of select="@extension"/>
					<fo:block />
				</xsl:if>
				<xsl:value-of select="@root"/>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<xsl:if test="not($id/@nullFlavor)">
				<xsl:if test="$id/@extension">
					<xsl:value-of select="$id/@extension"/>
					<fo:block />
				</xsl:if>
				<xsl:value-of select="$id/@root"/>
			</xsl:if>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
 
<!-- show-name -->
<xsl:template name="show-name">
	<xsl:param name="name"/>
	<xsl:choose>
		<xsl:when test="$name/n1:family">
			<xsl:if test="$name/n1:prefix">
				<xsl:value-of select="$name/n1:prefix"/>
				<xsl:text> </xsl:text>
			</xsl:if>
			<xsl:value-of select="$name/n1:given"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="$name/n1:family"/>
			<xsl:if test="$name/n1:suffix">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="$name/n1:suffix"/>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$name"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
 
<!-- show-gender -->
<xsl:template name="show-gender">
	<xsl:choose>
		<xsl:when test="@code   = &apos;M&apos;">
			<xsl:text>Male</xsl:text>
		</xsl:when>
		<xsl:when test="@code  = &apos;F&apos;">
			<xsl:text>Female</xsl:text>
		</xsl:when>
		<xsl:when test="@code  = &apos;U&apos;">
			<xsl:text>Undifferentiated</xsl:text>
		</xsl:when>
	</xsl:choose>
</xsl:template>
	
<!-- show-contactInfo -->
<xsl:template name="show-contactInfo">
	<xsl:param name="contact"/>
	<xsl:call-template name="show-address">
		<xsl:with-param name="address" select="$contact/n1:addr"/>
	</xsl:call-template>
	<xsl:call-template name="show-telecom">
		<xsl:with-param name="telecom" select="$contact/n1:telecom"/>
	</xsl:call-template>
</xsl:template>

<!-- show-address -->
<xsl:template name="show-address">
	<xsl:param name="address"/>
	<xsl:choose>
		<xsl:when test="$address">
			<xsl:if test="$address/@use">
				<xsl:text> </xsl:text>
				<xsl:call-template name="translateTelecomCode">
					<xsl:with-param name="code" select="$address/@use"/>
				</xsl:call-template>
				<xsl:text>:</xsl:text>
				<fo:block />
			</xsl:if>
			<xsl:for-each select="$address/n1:streetAddressLine">
			<xsl:value-of select="."/>
				<fo:block />
			</xsl:for-each>
			<xsl:if test="$address/n1:streetName">
				<xsl:value-of select="$address/n1:streetName"/>
				<xsl:text> </xsl:text>
				<xsl:value-of select="$address/n1:houseNumber"/>
				<fo:block />
			</xsl:if>
			<xsl:if test="string-length($address/n1:city)>0">
				<xsl:value-of select="$address/n1:city"/>
			</xsl:if>
			<xsl:if test="string-length($address/n1:state)>0">
				<xsl:text>,&#160;</xsl:text>
				<xsl:value-of select="$address/n1:state"/>
			</xsl:if>
			<xsl:if test="string-length($address/n1:postalCode)>0">
				<xsl:text>&#160;</xsl:text>
				<xsl:value-of select="$address/n1:postalCode"/>
			</xsl:if>
			<xsl:if test="string-length($address/n1:country)>0">
				<xsl:text>,&#160;</xsl:text>
				<xsl:value-of select="$address/n1:country"/>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>address not available</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<fo:block />
</xsl:template>
 
<!-- show-telecom -->
<xsl:template name="show-telecom">
	<xsl:param name="telecom"/>
	<xsl:choose>
		<xsl:when test="$telecom">
			<xsl:variable name="type" select="substring-before($telecom/@value, ':')"/>
			<xsl:variable name="value" select="substring-after($telecom/@value, ':')"/>
			<xsl:if test="$type">
				<xsl:call-template name="translateTelecomCode">
					<xsl:with-param name="code" select="$type"/>
				</xsl:call-template>
			<xsl:if test="@use">
				<xsl:text> (</xsl:text>
				<xsl:call-template name="translateTelecomCode">
				<xsl:with-param name="code" select="@use"/>
					</xsl:call-template>
				<xsl:text>)</xsl:text>
			</xsl:if>
				<xsl:text>: </xsl:text>
				<xsl:text> </xsl:text>
				<xsl:value-of select="$value"/>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>Telecom information not available</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<fo:block />
</xsl:template>

<!-- show-recipientType -->
<xsl:template name="show-recipientType">
	<xsl:param name="typeCode"/>
	<xsl:choose>
		<xsl:when test="$typeCode='PRCP'">Primary Recipient:</xsl:when>
		<xsl:when test="$typeCode='TRC'">Secondary Recipient:</xsl:when>
		<xsl:otherwise>Recipient:</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- Convert Telecom URL to display text -->
<xsl:template name="translateTelecomCode">
	<xsl:param name="code"/>
	<!--xsl:value-of select="document('voc.xml')/systems/system[@root=$code/@codeSystem]/code[@value=$code/@code]/@displayName"/-->
	<!--xsl:value-of select="document('codes.xml')/*/code[@code=$code]/@display"/-->
	<xsl:choose>
		<!-- lookup table Telecom URI -->
		<xsl:when test="$code='tel'">
			<xsl:text>Tel</xsl:text>
		</xsl:when>
		<xsl:when test="$code='fax'">
			<xsl:text>Fax</xsl:text>
		</xsl:when>
		<xsl:when test="$code='http'">
			<xsl:text>Web</xsl:text>
		</xsl:when>
		<xsl:when test="$code='mailto'">
			<xsl:text>Mail</xsl:text>
		</xsl:when>
		<xsl:when test="$code='H'">
			<xsl:text>Home</xsl:text>
		</xsl:when>
		<xsl:when test="$code='HV'">
			<xsl:text>Vacation Home</xsl:text>
		</xsl:when>
		<xsl:when test="$code='HP'">
			<xsl:text>Primary Home</xsl:text>
		</xsl:when>
		<xsl:when test="$code='WP'">
			<xsl:text>Work Place</xsl:text>
		</xsl:when>
		<xsl:when test="$code='PUB'">
			<xsl:text>Pub</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>{$code='</xsl:text>
			<xsl:value-of select="$code"/>
			<xsl:text>'?}</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- convert RoleClassAssociative code to display text -->
<xsl:template name="translateRoleAssoCode">
	<xsl:param name="code"/>
	<xsl:choose>
		<xsl:when test="$code='AFFL'">
			<xsl:text>affiliate</xsl:text>
		</xsl:when>
		<xsl:when test="$code='AGNT'">
			<xsl:text>agent</xsl:text>
		</xsl:when>
		<xsl:when test="$code='ASSIGNED'">
			<xsl:text>assigned entity</xsl:text>
		</xsl:when>
		<xsl:when test="$code='COMPAR'">
			<xsl:text>commissioning party</xsl:text>
		</xsl:when>
		<xsl:when test="$code='CON'">
			<xsl:text>contact</xsl:text>
		</xsl:when>
		<xsl:when test="$code='ECON'">
			<xsl:text>emergency contact</xsl:text>
		</xsl:when>
		<xsl:when test="$code='NOK'">
			<xsl:text>next of kin</xsl:text>
		</xsl:when>
		<xsl:when test="$code='SGNOFF'">
			<xsl:text>signing authority</xsl:text>
		</xsl:when>
		<xsl:when test="$code='GUARD'">
			<xsl:text>guardian</xsl:text>
		</xsl:when>
		<xsl:when test="$code='GUAR'">
			<xsl:text>guardian</xsl:text>
		</xsl:when>
		<xsl:when test="$code='CIT'">
			<xsl:text>citizen</xsl:text>
		</xsl:when>
		<xsl:when test="$code='COVPTY'">
			<xsl:text>covered party</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>{$code='</xsl:text>
			<xsl:value-of select="$code"/>
			<xsl:text>'?}</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- show time -->
<xsl:template name="show-time">
	<xsl:param name="datetime"/>
	<xsl:choose>
		<xsl:when test="not($datetime)">
			<xsl:call-template name="formatDateTime">
				<xsl:with-param name="date" select="@value"/>
			</xsl:call-template>
			<xsl:text> </xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:call-template name="formatDateTime">
				<xsl:with-param name="date" select="$datetime/@value"/>
			</xsl:call-template>
			<xsl:text> </xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- show assignedEntity -->
<xsl:template name="show-assignedEntity">
	<xsl:param name="asgnEntity"/>
	<xsl:choose>
		<xsl:when test="$asgnEntity/n1:assignedPerson/n1:name">
			<xsl:call-template name="show-name">
				<xsl:with-param name="name" select="$asgnEntity/n1:assignedPerson/n1:name"/>
			</xsl:call-template>
			<xsl:if test="$asgnEntity/n1:representedOrganization/n1:name">
				<xsl:text> of </xsl:text>
				<xsl:value-of select="$asgnEntity/n1:representedOrganization/n1:name"/>
			</xsl:if>
		</xsl:when>
		<xsl:when test="$asgnEntity/n1:representedOrganization">
			<xsl:value-of select="$asgnEntity/n1:representedOrganization/n1:name"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:for-each select="$asgnEntity/n1:id">
				<xsl:call-template name="show-id"/>
				<xsl:choose>
					<xsl:when test="position()!=last()">
						<xsl:text>, </xsl:text>
						</xsl:when>
					<xsl:otherwise>
						<fo:block />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
 
<!-- show relatedEntity -->
<xsl:template name="show-relatedEntity">
	<xsl:param name="relatedEntity"/>
	<xsl:choose>
		<xsl:when test="$relatedEntity/n1:relatedPerson/n1:name">
			<xsl:call-template name="show-name">
				<xsl:with-param name="name" select="$relatedEntity/n1:relatedPerson/n1:name"/>
			</xsl:call-template>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- show associatedEntity -->
<xsl:template name="show-associatedEntity">
	<xsl:param name="assoEntity"/>
	<xsl:choose>
		<xsl:when test="$assoEntity/n1:associatedPerson">
			<xsl:for-each select="$assoEntity/n1:associatedPerson/n1:name">
			 <xsl:call-template name="show-name">
				<xsl:with-param name="name" select="."/>
			 </xsl:call-template>
			  <fo:block />
			</xsl:for-each>
		</xsl:when>
		<xsl:when test="$assoEntity/n1:scopingOrganization">
			<xsl:for-each select="$assoEntity/n1:scopingOrganization">
				<xsl:if test="n1:name">
					<xsl:call-template name="show-name">
						<xsl:with-param name="name" select="n1:name"/>
					</xsl:call-template>
					<fo:block />
				</xsl:if>
				<xsl:if test="n1:standardIndustryClassCode">
					<xsl:value-of select="n1:standardIndustryClassCode/@displayName"/>
					<xsl:text> code:</xsl:text>
					<xsl:value-of select="n1:standardIndustryClassCode/@code"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:when>
		<xsl:when test="$assoEntity/n1:code">
			<xsl:call-template name="show-code">
				<xsl:with-param name="code" select="$assoEntity/n1:code"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:when test="$assoEntity/n1:id">
			<xsl:value-of select="$assoEntity/n1:id/@extension"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="$assoEntity/n1:id/@root"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- show code 
if originalText present, return it, otherwise, check and return attribute: display name
-->
<xsl:template name="show-code">
	<xsl:param name="code"/>
	<xsl:variable name="this-codeSystem">
		<xsl:value-of select="$code/@codeSystem"/>
	</xsl:variable>
	<xsl:variable name="this-code">
		<xsl:value-of select="$code/@code"/>  
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="$code/n1:originalText">
			<xsl:value-of select="$code/n1:originalText"/>
		</xsl:when>
		<xsl:when test="$code/@displayName">
			<xsl:value-of select="$code/@displayName"/>
		</xsl:when>
		<!--
		<xsl:when test="$the-valuesets/*/voc:system[@root=$this-codeSystem]/voc:code[@value=$this-code]/@displayName">
			<xsl:value-of select="$the-valuesets/*/voc:system[@root=$this-codeSystem]/voc:code[@value=$this-code]/@displayName"/>
		</xsl:when>
		-->
		<xsl:otherwise>
			<xsl:value-of select="$this-code"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- show classCode -->
<xsl:template name="show-actClassCode">
	<xsl:param name="clsCode"/>
	<xsl:choose>
		<xsl:when test=" $clsCode = 'ACT' ">
			<xsl:text>healthcare service</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'ACCM' ">
			<xsl:text>accommodation</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'ACCT' ">
			<xsl:text>account</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'ACSN' ">
			<xsl:text>accession</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'ADJUD' ">
			<xsl:text>financial adjudication</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'CONS' ">
			<xsl:text>consent</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'CONTREG' ">
			<xsl:text>container registration</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'CTTEVENT' ">
			<xsl:text>clinical trial timepoint event</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'DISPACT' ">
			<xsl:text>disciplinary action</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'ENC' ">
			<xsl:text>encounter</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'INC' ">
			<xsl:text>incident</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'INFRM' ">
			<xsl:text>inform</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'INVE' ">
			<xsl:text>invoice element</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'LIST' ">
			<xsl:text>working list</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'MPROT' ">
			<xsl:text>monitoring program</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'PCPR' ">
			<xsl:text>care provision</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'PROC' ">
			<xsl:text>procedure</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'REG' ">
			<xsl:text>registration</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'REV' ">
			<xsl:text>review</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'SBADM' ">
			<xsl:text>substance administration</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'SPCTRT' ">
			<xsl:text>speciment treatment</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'SUBST' ">
			<xsl:text>substitution</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'TRNS' ">
			<xsl:text>transportation</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'VERIF' ">
			<xsl:text>verification</xsl:text>
		</xsl:when>
		<xsl:when test=" $clsCode = 'XACT' ">
			<xsl:text>financial transaction</xsl:text>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- show participationType -->
<xsl:template name="show-participationType">
	<xsl:param name="ptype"/>
	<xsl:choose>
		<xsl:when test=" $ptype='PPRF' ">
			<xsl:text>primary performer</xsl:text>
		</xsl:when>
		<xsl:when test=" $ptype='PRF' ">
			<xsl:text>performer</xsl:text>
		</xsl:when>
		<xsl:when test=" $ptype='VRF' ">
			<xsl:text>verifier</xsl:text>
		</xsl:when>
		<xsl:when test=" $ptype='SPRF' ">
			<xsl:text>secondary performer</xsl:text>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- show participationFunction -->
<xsl:template name="show-participationFunction">
	<xsl:param name="pFunction"/>
	<xsl:choose>
		<xsl:when test=" $pFunction = 'CP' ">
			<xsl:text>Consulting or Other Physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'RP' ">
			<xsl:text>Referring Physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'PP' ">
			<xsl:text>Primary Care Physician</xsl:text>
		</xsl:when>
		<!--   
		<xsl:when test=" $pFunction = 'ATTPHYS' ">
			<xsl:text>attending physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'DISPHYS' ">
			<xsl:text>discharging physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'FASST' ">
			<xsl:text>first assistant surgeon</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'MDWF' ">
			<xsl:text>midwife</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'NASST' ">
			<xsl:text>nurse assistant</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'PCP' ">
			<xsl:text>primary care physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'PRISURG' ">
			<xsl:text>primary surgeon</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'RNDPHYS' ">
			<xsl:text>rounding physician</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'SASST' ">
			<xsl:text>second assistant surgeon</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'SNRS' ">
			<xsl:text>scrub nurse</xsl:text>
		</xsl:when>
		<xsl:when test=" $pFunction = 'TASST' ">
			<xsl:text>third assistant</xsl:text>
		</xsl:when>
		-->   
	</xsl:choose>
</xsl:template>

<xsl:template name="formatDateTime">
	<xsl:param name="date"/>
	<!-- month -->
	<xsl:variable name="month" select="substring ($date, 5, 2)"/>
	<xsl:choose>
		<xsl:when test="$month='01'">
			<xsl:text>January </xsl:text>
		</xsl:when>
		<xsl:when test="$month='02'">
			<xsl:text>February </xsl:text>
		</xsl:when>
		<xsl:when test="$month='03'">
			<xsl:text>March </xsl:text>
		</xsl:when>
		<xsl:when test="$month='04'">
			<xsl:text>April </xsl:text>
		</xsl:when>
		<xsl:when test="$month='05'">
			<xsl:text>May </xsl:text>
		</xsl:when>
		<xsl:when test="$month='06'">
			<xsl:text>June </xsl:text>
		</xsl:when>
		<xsl:when test="$month='07'">
			<xsl:text>July </xsl:text>
		</xsl:when>
		<xsl:when test="$month='08'">
			<xsl:text>August </xsl:text>
		</xsl:when>
		<xsl:when test="$month='09'">
			<xsl:text>September </xsl:text>
		</xsl:when>
		<xsl:when test="$month='10'">
			<xsl:text>October </xsl:text>
		</xsl:when>
		<xsl:when test="$month='11'">
			<xsl:text>November </xsl:text>
		</xsl:when>
		<xsl:when test="$month='12'">
			<xsl:text>December </xsl:text>
		</xsl:when>
	</xsl:choose>
	<!-- day -->
	<xsl:choose>
		<xsl:when test='substring ($date, 7, 1)="0"'>
			<xsl:value-of select="substring ($date, 8, 1)"/>
			<xsl:text>, </xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="substring ($date, 7, 2)"/>
			<xsl:text>, </xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<!-- year -->
	<xsl:value-of select="substring ($date, 1, 4)"/>
	<!-- time and US timezone -->
	<xsl:if test="string-length($date) > 8">
		<xsl:text>, </xsl:text>
		<!-- time -->
		<xsl:variable name="time">
			<xsl:value-of select="substring($date,9,6)"/>
		</xsl:variable>
		<xsl:variable name="hh">
			<xsl:value-of select="substring($time,1,2)"/>
		</xsl:variable>
		<xsl:variable name="mm">
			<xsl:value-of select="substring($time,3,2)"/>
		</xsl:variable>
		<xsl:variable name="ss">
			<xsl:value-of select="substring($time,5,2)"/>
		</xsl:variable>
		<xsl:if test="string-length($hh)&gt;1">
			<xsl:value-of select="$hh"/>
			<xsl:if test="string-length($mm)&gt;1 and not(contains($mm,'-')) and not (contains($mm,'+'))">
				<xsl:text>:</xsl:text>
				<xsl:value-of select="$mm"/>
				<xsl:if test="string-length($ss)&gt;1 and not(contains($ss,'-')) and not (contains($ss,'+'))">
					<xsl:text>:</xsl:text>
					<xsl:value-of select="$ss"/>
				</xsl:if>
			</xsl:if>
		</xsl:if>
		<!-- time zone -->
		<xsl:variable name="tzon">
			<xsl:choose>
				<xsl:when test="contains($date,'+')">
					<xsl:text>+</xsl:text>
					<xsl:value-of select="substring-after($date, '+')"/>
				</xsl:when>
				<xsl:when test="contains($date,'-')">
					<xsl:text>-</xsl:text>
					<xsl:value-of select="substring-after($date, '-')"/>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<!-- reference: http://www.timeanddate.com/library/abbreviations/timezones/na/ -->
			<xsl:when test="$tzon = '-0500' ">
				<xsl:text>, EST</xsl:text>
			</xsl:when>
			<xsl:when test="$tzon = '-0600' ">
				<xsl:text>, CST</xsl:text>
			</xsl:when>
			<xsl:when test="$tzon = '-0700' ">
				<xsl:text>, MST</xsl:text>
			</xsl:when>
			<xsl:when test="$tzon = '-0800' ">
				<xsl:text>, PST</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text> </xsl:text>
				<xsl:value-of select="$tzon"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
</xsl:template>

<!-- convert to lower case -->
<xsl:template name="caseDown">
	<xsl:param name="data"/>
	<xsl:if test="$data">
		<xsl:value-of select="translate($data, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
	</xsl:if>
</xsl:template>

<!-- convert to upper case -->
<xsl:template name="caseUp">
	<xsl:param name="data"/>
	<xsl:if test="$data">
		<xsl:value-of select="translate($data,'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
	</xsl:if>
</xsl:template>

<!-- convert first character to upper case -->
<xsl:template name="firstCharCaseUp">
	<xsl:param name="data"/>
	<xsl:if test="$data">
		<xsl:call-template name="caseUp">
			<xsl:with-param name="data" select="substring($data,1,1)"/>
		</xsl:call-template>
		<xsl:value-of select="substring($data,2)"/>
	</xsl:if>
</xsl:template>

 <!-- show-noneFlavor -->
 <!-- to do list -->
 <xsl:template name="show-noneFlavor">  
 </xsl:template> 
 
<!-- For wrapper Text -->
<xsl:template name="zero_width_space_1">
	<xsl:param name="data"/>
	<xsl:param name="counter" select="0"/>
	<xsl:choose>
	    <xsl:when test='contains($data, " ") and string-length(substring-before($data, " ")) &lt; 12'>
			<xsl:variable name="varname"> 
				<xsl:value-of select='substring-after($data, " ")'/>
			</xsl:variable>
			<xsl:choose>
			    <xsl:when test='contains($varname, " ")'>
					<xsl:value-of select='concat(substring-before($data, " "), " ")'/>
					<xsl:call-template name="zero_width_space_1">
						<xsl:with-param name="data" select="$varname"/>
					</xsl:call-template>
				</xsl:when>
			    <xsl:when test='string-length($varname) &gt; 12'>
					<xsl:value-of select='concat(substring-before($data, " "), " ")'/>
					<xsl:call-template name="zero_width_space_1">
						<xsl:with-param name="data" select="$varname"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$data"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:when test="$counter &lt; string-length($data) + 1">
			<xsl:value-of select='concat(substring($data,$counter,1),"&#8203;")'/>
			<xsl:call-template name="zero_width_space_2">
				<xsl:with-param name="data" select="$data"/>
				<xsl:with-param name="counter" select="$counter+1"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="zero_width_space_2">
	<xsl:param name="data"/>
	<xsl:param name="counter"/>
	<xsl:value-of select='concat(substring($data,$counter,1),"&#8203;")'/>
	<xsl:call-template name="zero_width_space_1">
		<xsl:with-param name="data" select="$data"/>
		<xsl:with-param name="counter" select="$counter+1"/>
	</xsl:call-template>
</xsl:template>
 
</xsl:stylesheet>