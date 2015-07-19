<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    <xsl:key name="overridenFunc" match="function" use="@name"/>
    
<xsl:template match="uXML">
    <!-- No. of functions -->
    <xsl:text disable-output-escaping="yes">No. of functions defined : </xsl:text>
    <xsl:value-of select="count(descendant::function)"/>
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print function names -->
    <xsl:for-each select="descendant::function">
        <xsl:value-of select="./@name"/>
        <!-- Print parameters -->
        <xsl:text>(</xsl:text>
        <xsl:for-each select="descendant::automatic-Var[1]/var">
            <xsl:value-of select="./@type"/>
            <xsl:if test="not(position()=last())">
                <xsl:text>,</xsl:text>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>)</xsl:text>
        <xsl:text>&#x0a;</xsl:text>
    </xsl:for-each>
    
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- No. of function calls -->
    <xsl:text disable-output-escaping="yes">No. of functions calls : </xsl:text>
    <xsl:value-of select="count(descendant::call[not(variable/@name ='#new#')])"/>
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print function called -->
    <xsl:for-each select="descendant::call[not(variable/@name ='#new#')]">
        <xsl:value-of select="./variable/@name"/>
        <xsl:text>&#x0a;</xsl:text>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- No. of variables defined -->
    <xsl:text disable-output-escaping="yes">No. of variables defined : </xsl:text>
    <xsl:value-of select="count(descendant::var)"/>
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print variable definition details-->
    <xsl:for-each select="descendant::var">
        <xsl:value-of select="./@name"/>
        <xsl:text> : </xsl:text>
        <xsl:value-of select="./@type"/>
        <xsl:text>&#x0a;</xsl:text>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    
    <!-- No. of constants used -->
    <xsl:text disable-output-escaping="yes">No. of constants used : </xsl:text>
    <xsl:value-of select="count(descendant::constant)"/>
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print constant details-->
    <xsl:for-each select="descendant::constant">
        <xsl:value-of select="./@type"/>
        <xsl:text> : </xsl:text>
        <xsl:value-of select="./@value"/>
        <xsl:text>&#x0a;</xsl:text>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- MCC -->
    <xsl:text disable-output-escaping="yes"> McCabe Cyclomatic Complexity : </xsl:text>
    <xsl:value-of select="count(descendant::operator[
        @value='(' or @value='(=' or @value=')' or
        @value=')=' or @value='==' or @value='!=' or
        @value='$$' or @value='||' or @value='!']) + 
        count(descendant::for-loop) + 
        count(descendant::iterate)
        "/>
    <xsl:text>&#x0a;</xsl:text>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- Number of classes defined  -->
    <xsl:text disable-output-escaping="yes">No. of classes defined : </xsl:text>
    <xsl:value-of select="count(descendant::class-type)"/>
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print class names -->
    <xsl:for-each select="descendant::class-type">
        <xsl:value-of select="./@name"/>
        <xsl:text>&#x0a;</xsl:text>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- Number of variables/class -->
    <xsl:text disable-output-escaping="yes">No. of variables defined per class : </xsl:text>
    
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print class names -->
    <xsl:for-each select="descendant::class-type">
        <xsl:value-of select="./@name"/>
        <xsl:text>: </xsl:text>
        <xsl:value-of select="count(descendant::function)"/>
        <xsl:text>&#x0a;</xsl:text>
        <!-- Print variable names in current class -->
        <xsl:for-each select="descendant::var">
            <xsl:text>&#x09;</xsl:text>
            <xsl:value-of select="./@name"/>
            <xsl:text>:</xsl:text>
            <xsl:value-of select="./@type"/>
            <xsl:text>&#x0a;</xsl:text>
        </xsl:for-each>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- Number of methods/class -->
    <xsl:text disable-output-escaping="yes">No. of methods per class : </xsl:text>
    
    <xsl:text>&#x0a;</xsl:text>
    <!-- Print class names -->
    <xsl:for-each select="descendant::class-type">
        <xsl:value-of select="./@name"/>
        <xsl:text>: </xsl:text>
        <xsl:value-of select="count(descendant::function)"/>
        <xsl:text>&#x0a;</xsl:text>
        <!-- Print method names in current class -->
        <xsl:for-each select="descendant::function">
            <xsl:text>&#x09;</xsl:text>
            <xsl:value-of select="./@name"/>
            <!-- Print parameters -->
            <xsl:text>(</xsl:text>
            <xsl:for-each select="descendant::automatic-Var[1]/var">
                <xsl:value-of select="./@type"/>
                <xsl:if test="not(position()=last())">
                    <xsl:text>,</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>)</xsl:text>
            <xsl:text>&#x0a;</xsl:text>
        </xsl:for-each>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- Number of constructor in a class -->
    <xsl:text disable-output-escaping="yes">No. of constructors per class : </xsl:text>
    
    <xsl:text>&#x0a;</xsl:text>
    <!-- Iterate over class names -->
    <xsl:for-each select="descendant::class-type">
        <xsl:value-of select="./@name"/>
        <xsl:text>: </xsl:text>
        <xsl:value-of select="count(descendant::constructor)"/>
        <xsl:text>&#x0a;</xsl:text>
        <!-- Print constructors in current class -->
        <xsl:for-each select="descendant::constructor">
            <xsl:text>&#x09;</xsl:text>
            <xsl:value-of select="./@name"/>
            <!-- Print parameters -->
            <xsl:text>(</xsl:text>
            <xsl:for-each select="descendant::automatic-Var[1]/var">
                <xsl:value-of select="./@type"/>
                <xsl:if test="not(position()=last())">
                    <xsl:text>,</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>)</xsl:text>
            <xsl:text>&#x0a;</xsl:text>
        </xsl:for-each>
    </xsl:for-each>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    
    <!-- Number of constructor calls -->
    <xsl:text disable-output-escaping="yes">No. of constructor calls : </xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    <xsl:value-of select="count(descendant::call[variable/@name ='#new#'])"/>
    <xsl:text>&#x0a;</xsl:text>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
    <!-- Methods overriden -->
    <xsl:text disable-output-escaping="yes">Methods overriden : </xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    <xsl:for-each select="descendant::function[count(. | key('overridenFunc', @name)[1]) = 1]">
        <xsl:if test="count(key('overridenFunc', @name)) &gt; 1">
            <xsl:value-of select="@name" />  
            <xsl:text> : </xsl:text>
            <xsl:value-of select="count(key('overridenFunc', @name))" />
        </xsl:if>
    </xsl:for-each>
    <xsl:text>&#x0a;</xsl:text>
    <xsl:text>______________________________</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
    
</xsl:template>
</xsl:stylesheet>
