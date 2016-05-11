
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:ccd="urn:hl7-org:v3">

	<xsl:attribute-set name="table.properties">
		<xsl:attribute name="border">0.5pt solid blue</xsl:attribute>
		<xsl:attribute name="border-spacing">3pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="subtable.properties">
		<xsl:attribute name="border">solid blue</xsl:attribute>
		<xsl:attribute name="border-spacing">0pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="subtable.body.properties">
		<xsl:attribute name="padding">3pt</xsl:attribute>
		<xsl:attribute name="border">solid blue</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>

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

	<xsl:attribute-set name="b.properties">
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
		<xsl:attribute name="border-color">blue</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="td.properties">
		<xsl:attribute name="padding">1pt</xsl:attribute>
		<xsl:attribute name="border-style">solid</xsl:attribute>
		<xsl:attribute name="border-width">1pt</xsl:attribute>
		<xsl:attribute name="border-color">blue</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
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

	<xsl:attribute-set name="page.number.properties">
		<xsl:attribute name="text-align">end</xsl:attribute>
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
	</xsl:attribute-set>

	<xsl:output indent="yes" />

	<xsl:template match="ccd:ClinicalDocument">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<xsl:call-template name="PageMaster" />
			<fo:page-sequence master-reference="simple">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block xsl:use-attribute-sets="page.number.properties">
						Page: <fo:page-number />
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<fo:block xsl:use-attribute-sets="title.properties">
						<xsl:choose>
							<xsl:when test="ccd:title">
								<xsl:value-of select="ccd:title" />
							</xsl:when>
							<xsl:otherwise>
								Clinical Document
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>
					<fo:block>
						<fo:leader />
					</fo:block>
					<!-- Process all templates under ClinicalDocument -->
					<xsl:apply-templates />
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

	<!-- patient data -->
	<xsl:template match="ccd:recordTarget">
		<xsl:for-each select="ccd:patientRole">
			<fo:block xsl:use-attribute-sets="section.header.properties">
				Patient Demographics
			</fo:block>
			<fo:table xsl:use-attribute-sets="table.properties">
				<fo:table-column column-width="2in" />
				<fo:table-column column-width="5in" />
				
				<fo:table-body>
					<xsl:for-each select="ccd:patient">
						<fo:table-row>
							<fo:table-cell xsl:use-attribute-sets="table.header.properties">
								<fo:block>Name</fo:block>
							</fo:table-cell>
							<fo:table-cell xsl:use-attribute-sets="table.body.properties">
								<fo:block>
									<xsl:value-of select="ccd:name/ccd:given"/>
									<xsl:text> </xsl:text>
									<xsl:value-of select="ccd:name/ccd:family"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block>Gender</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<fo:block>
								<xsl:variable name="sex" select="ccd:patient/ccd:administrativeGenderCode/@code"/>
								<xsl:choose>
								   <xsl:when test="$sex='M'">Male</xsl:when>
								   <xsl:when test="$sex='F'">Female</xsl:when>
								</xsl:choose>							
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block>Date of Birth</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<fo:block>
								<xsl:call-template name="formatDate">
   		 		                        <xsl:with-param name="date" select="ccd:patient/ccd:birthTime/@value"/>
                              </xsl:call-template>		
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block>Identification Numbers</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<fo:block>
								<xsl:value-of select="ccd:id/@extension"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block>Address / Phone</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<xsl:for-each select="ccd:addr/ccd:streetAddressLine">
								<fo:block> <xsl:value-of select="."/> </fo:block>
							</xsl:for-each>
							<fo:block>
								<xsl:if test="ccd:addr/ccd:city">
									<xsl:value-of select="ccd:addr/ccd:city"/>, 
								</xsl:if>	
								<xsl:value-of select="ccd:addr/ccd:state"/>
								<xsl:value-of select="ccd:addr/ccd:postalCode"/>
							</fo:block>
							<fo:block> <xsl:value-of select="ccd:addr/ccd:country"/> </fo:block>
							<xsl:if test="ccd:telecom">
								<fo:block>
									<xsl:value-of select="ccd:telecom/@value"/>
								</fo:block>
							</xsl:if>	
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</xsl:for-each>
	</xsl:template>

	<!-- renders participants -->
	<xsl:template match="ccd:participant">
		<fo:block>
			<fo:leader />
		</fo:block>
		<fo:block xsl:use-attribute-sets="section.header.properties">
			Participant
		</fo:block>
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
							<xsl:value-of select="ccd:associatedEntity/ccd:associatedPerson/ccd:name/ccd:given"/>
							<xsl:text> </xsl:text>
							<xsl:value-of select="ccd:associatedEntity/ccd:associatedPerson/ccd:name/ccd:family"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>

				<xsl:for-each select="ccd:associatedEntity/ccd:addr/ccd:streetAddressLine">
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block>Address Line</fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<fo:block> <xsl:value-of select="."/> </fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:for-each>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<fo:block>City</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="table.body.properties">
						<fo:block> <xsl:value-of select="ccd:associatedEntity/ccd:addr/ccd:city"/> </fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<fo:block>State</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="table.body.properties">
						<fo:block> <xsl:value-of select="ccd:associatedEntity/ccd:addr/ccd:state"/> </fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<fo:block>Postal Code</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="table.body.properties">
						<fo:block> <xsl:value-of select="ccd:associatedEntity/ccd:addr/ccd:postalCode"/> </fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<fo:block>Country</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="table.body.properties">
						<fo:block> <xsl:value-of select="ccd:addr/ccd:country"/> </fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<!-- Components -->
	<xsl:template match="ccd:component/ccd:structuredBody">
		  <fo:block>
		    <fo:leader />
		  </fo:block>
		<xsl:for-each select="ccd:component">
			<fo:block xsl:use-attribute-sets="section.header.properties">
				<xsl:value-of select="ccd:section/ccd:title" />
			</fo:block>
				<xsl:variable name="varname"> 
					<xsl:value-of select="ccd:section/ccd:text/ccd:table"/>         
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="$varname=''">
						<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="7in" />
						<fo:table-body>
						<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
							<fo:block>
								<xsl:if test="ccd:section/ccd:text=''">
									<xsl:text xml:space="preserve">&lt;p&gt;</xsl:text>
								</xsl:if>
								<xsl:apply-templates select="ccd:section/ccd:text"/>
							</fo:block>
						</fo:table-cell>
						</fo:table-row>
						</fo:table-body>
						</fo:table>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="ccd:section/ccd:text"/>
					</xsl:otherwise>
				</xsl:choose>
			<fo:block>
				<fo:leader />
			</fo:block>
		</xsl:for-each>
	</xsl:template>


	<!-- render paragraph -->
	<xsl:template match="ccd:paragraph">
		<fo:block xsl:use-attribute-sets="b.properties">
			<xsl:value-of select="." />
		</fo:block>
		<fo:block>
			<fo:leader />
		</fo:block>
	</xsl:template>
	
	<xsl:template match="ccd:list">
		<xsl:for-each select="ccd:item">
			<fo:block xsl:use-attribute-sets="b.properties">
				<xsl:apply-templates />
			</fo:block>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="ccd:list[@listType='ordered']">
		<xsl:for-each select="ccd:item">
			<fo:block><xsl:apply-templates /></fo:block>
		</xsl:for-each>
	</xsl:template>

	<!-- renders html table -->
	<xsl:template match="ccd:table">
		<xsl:choose>
			<xsl:when test="ccd:tbody/ccd:tr/ccd:th">
					<xsl:variable name="ds" select="ccd:tbody" />
					<xsl:for-each select="ccd:thead/ccd:tr/ccd:th">
					<xsl:variable name="pos" select="position()" />
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
						<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block> <xsl:value-of select="."/> </fo:block>
						</fo:table-cell>
							<xsl:choose>
								<xsl:when test="$pos > 1">
									<fo:table-cell>
										<fo:table xsl:use-attribute-sets="subtable.properties">
										<fo:table-column column-width="2in" />
										<fo:table-column column-width="3in" />
										<fo:table-body>
										<xsl:for-each select="$ds/ccd:tr">
										<fo:table-row >
										<fo:table-cell xsl:use-attribute-sets="subtable.body.properties">
											<fo:block> <xsl:value-of select="ccd:th"/> </fo:block> 
										</fo:table-cell>
										<fo:table-cell xsl:use-attribute-sets="subtable.body.properties">
											<fo:block>  <xsl:value-of select="ccd:td[$pos - 1]"/> </fo:block>  
										</fo:table-cell>
										</fo:table-row>
											</xsl:for-each> 
										</fo:table-body>
										</fo:table>
									</fo:table-cell>
								</xsl:when>	
								<xsl:otherwise>
									<fo:table-cell xsl:use-attribute-sets="table.body.properties">
										<fo:block></fo:block>
									</fo:table-cell>
								</xsl:otherwise>
							</xsl:choose>
						</fo:table-row >
						</fo:table-body>
					</fo:table>
				</xsl:for-each>
			</xsl:when>
			<xsl:when test="ccd:tbody/ccd:tr/ccd:td/@colspan">
				<xsl:variable name="ds" select="ccd:tbody" />
				<xsl:for-each select="ccd:thead/ccd:tr/ccd:th">
				<xsl:variable name="pos" select="position()" />
				<xsl:if test="$pos > 1">
					<fo:table xsl:use-attribute-sets="table.properties">
						<fo:table-column column-width="2in" />
						<fo:table-column column-width="5in" />
						<fo:table-body>
						<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
							<fo:block> <xsl:value-of select="."/> </fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<xsl:choose>
								<xsl:when test="@colspan">
									<fo:table xsl:use-attribute-sets="table.properties">
									<fo:table-column column-width="5in" />
									<fo:table-body>
									<fo:table-cell xsl:use-attribute-sets="table.header.properties">
										<fo:block> <xsl:value-of select="ccd:td[1]"/> </fo:block> 
									</fo:table-cell>
									</fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table xsl:use-attribute-sets="subtable.properties">
									<fo:table-column column-width="2in" />
									<fo:table-column column-width="3in" />
									<fo:table-body>
									<xsl:for-each select="$ds/ccd:tr">
										<fo:table-row >
											<xsl:choose>
												<xsl:when test="ccd:td/@colspan">
													<fo:table-cell xsl:use-attribute-sets="table.header.properties">
														<fo:block><xsl:value-of select="ccd:td[1]"/> </fo:block> 
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="table.header.properties">
														<fo:block> </fo:block>  
													</fo:table-cell>
												</xsl:when>
												<xsl:otherwise>
													<fo:table-cell xsl:use-attribute-sets="subtable.body.properties">
														<fo:block><xsl:value-of select="ccd:td[1]"/> </fo:block> 
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="subtable.body.properties">
														<fo:block>  <xsl:value-of select="ccd:td[$pos]"/> </fo:block>  
													</fo:table-cell>
												</xsl:otherwise>
											</xsl:choose>
										</fo:table-row>
										</xsl:for-each> 
									</fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:table-cell>
						</fo:table-row >
						</fo:table-body>
						</fo:table>
					</xsl:if>	
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="ds" select="ccd:thead/ccd:tr" />
				<xsl:for-each select="ccd:tbody/ccd:tr">
					<fo:table xsl:use-attribute-sets="table.properties">
					<fo:table-column column-width="2in" />
					<fo:table-column column-width="5in" />
					<fo:table-body xsl:use-attribute-sets="table.body.properties">
					<xsl:for-each select="ccd:td">
						<xsl:variable name="pos" select="position()" />
						<fo:table-row >
						<fo:table-cell xsl:use-attribute-sets="table.header.properties">
						<xsl:if test="@colspan">
						<xsl:attribute name="number-columns-spanned">
						<xsl:value-of select="@colspan"/>
						</xsl:attribute>
						</xsl:if>
						<xsl:if test="@align">
						<xsl:attribute name="text-align">
						<xsl:value-of select="@align"/>
						</xsl:attribute>
						</xsl:if>
						<fo:block> <xsl:value-of select="$ds/ccd:th[$pos]"/> </fo:block>
						</fo:table-cell>
						<fo:table-cell xsl:use-attribute-sets="table.body.properties">
						<xsl:if test="@colspan">
						<xsl:attribute name="number-columns-spanned">
						<xsl:value-of select="@colspan"/>
						</xsl:attribute>
						</xsl:if>
						<xsl:if test="@align">
						<xsl:attribute name="text-align">
						<xsl:value-of select="@align"/>
						</xsl:attribute>
						</xsl:if>
						<fo:block> <xsl:value-of select="."/> </fo:block>
						</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
					</fo:table-body>
					</fo:table>
					<fo:block>
						<fo:leader />
					</fo:block>
				</xsl:for-each>	
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="ccd:thead">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="ccd:tfoot">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="ccd:tbody">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="ccd:colgroup">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="ccd:col">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="ccd:tr">
		<fo:table-row >
			<xsl:apply-templates/>
		</fo:table-row >
	</xsl:template>

	<xsl:template match="ccd:th">
		<fo:table-cell xsl:use-attribute-sets="th.properties">
			<fo:block>
				<xsl:apply-templates/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="ccd:td">
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

	<xsl:template match="ccd:table/ccd:caption">
		<fo:block  xsl:use-attribute-sets="b.properties">
			<xsl:apply-templates/>
		</fo:block>
	</xsl:template>
<!--  Format Date 
    
      outputs a date in Month Day, Year form
      e.g., 19991207  ==> December 07, 1999
-->
<xsl:template name="formatDate">
        <xsl:param name="date"/>
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
        <xsl:value-of select="substring ($date, 1, 4)"/>
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