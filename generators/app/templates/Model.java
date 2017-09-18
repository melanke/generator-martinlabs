package <%= props.modelPackage %>;

import br.com.martinlabs.commons.LanguageHolder;
import br.com.martinlabs.commons.ResultSetWrapper;
import br.com.martinlabs.commons.Validator;
import br.com.martinlabs.commons.exceptions.RespException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

<% if (table.comment) { %>
@ApiModel(description="<%= table.comment %>")<%
}
%>
public class <%= table.className %> {
<% 
for (var i in table.columns) { 
    var c = table.columns[i]; 
    if (c.is_nullable === "NO" && c.column_comment) {
%>
    @ApiModelProperty(required = true, value = "<%= c.column_comment %>")<%
    } else if (c.is_nullable === "NO") {
%>
    @ApiModelProperty(required = true)<%
    } else if (c.column_comment) {
%>
    @ApiModelProperty(value = "<%= c.column_comment %>")<%
    }

    if (!c.referencedTable) {
%>
    private <%= c.javaType %> <%= c.propertyName %>;
<% 
    } else { 
%>
    private <%= c.referencedTable.className %> <%= c.notIdPropertyName %>;
<% 
    } 
} 
%>
<% 
for (var i in table.NtoNcolumns) { 
    var cn = table.NtoNcolumns[i]; 
%>
    private List<<%= cn.otherTable.className %>> <%= cn.NtoNtable.classLowerCamel %>;
<% 
} 
%>
    public <%= table.className %>() {
    }

    public <%= table.className %>(<%= table.className %> other) {
<% 
for (var i in table.columns) { 
    var c = table.columns[i]; 
    if (!c.referencedTable) {
%>
        this.<%= c.propertyName %> = other.<%= c.propertyName %>;<% 
    } else { 
%>
        this.<%= c.notIdPropertyName %> = other.<%= c.notIdPropertyName %>;<% 
    } 
} 

for (var i in table.NtoNcolumns) { 
    var cn = table.NtoNcolumns[i]; 
%>
        this.<%= cn.NtoNtable.classLowerCamel %> = other.<%= cn.NtoNtable.classLowerCamel %>;<% 
} 
%>
    }

    public void validate(boolean updating, LanguageHolder lang) {<% 
for (var i in table.columns) { 
    var c = table.columns[i]; 
    if (c.is_nullable === "NO") { 
        if (c.smartType == "password") {
%>
        if (!updating) {
            if (get<%= c.propertyNameUpper %>() == null || get<%= c.propertyNameUpper %>().isEmpty()) {
                throw new RespException(<%= c.ordinal_position %>,  lang.cannotBeNull("<%= c.propertyNatural %>"));
            }
        }<% 
        } else if (c.javaType === "String") {
%>
        if (get<%= c.propertyNameUpper %>() == null || get<%= c.propertyNameUpper %>().isEmpty()) {
            throw new RespException(<%= c.ordinal_position %>,  lang.cannotBeNull("<%= c.propertyNatural %>"));
        }<% 
        } else if (c.javaType === "Date" && c.smartType != "createTime" && c.smartType != "updateTime") {
%>
        if (get<%= c.propertyNameUpper %>() == null) {
            throw new RespException(<%= c.ordinal_position %>,  lang.cannotBeNull("<%= c.propertyNatural %>"));
        }<% 
        }
    }

    if (c.javaType === "String") {

        if (c.is_nullable === "NO" && c.smartType != "password") {
%>
        if (get<%= c.propertyNameUpper %>().length() > <%= Math.min(65500, c.character_maximum_length) %>) {
            throw new RespException(<%= c.ordinal_position %>, lang.lengthCannotBeMoreThan("<%= c.propertyNatural %>", <%= Math.min(65500, c.character_maximum_length) %>));
        }<%
        } else {
%>
        if (get<%= c.propertyNameUpper %>() != null && get<%= c.propertyNameUpper %>().length() > <%= Math.min(65500, c.character_maximum_length) %>) {
            throw new RespException(<%= c.ordinal_position %>, lang.lengthCannotBeMoreThan("<%= c.propertyNatural %>", <%= Math.min(65500, c.character_maximum_length) %>));
        }<%
        }

        if (c.smartType === "email") {
            if (c.is_nullable === "NO") {
%>
        if (!Validator.isEmail(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidEmail("<%= c.propertyNatural %>"));
        }<%
            } else {
%>
        if (get<%= c.propertyNameUpper %>() != null && !Validator.isEmail(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidEmail("<%= c.propertyNatural %>"));
        }<%
            }
        } else if (c.smartType === "cpf") {
            if (c.is_nullable === "NO") {
%>
        if (!Validator.isCPF(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidCPF("<%= c.propertyNatural %>"));
        }<%
            } else {
%>
        if (get<%= c.propertyNameUpper %>() != null && !Validator.isCPF(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidCPF("<%= c.propertyNatural %>"));
        }<%
            }
        } else if (c.smartType === "cnpj") {
            if (c.is_nullable === "NO") {
%>
        if (!Validator.isCNPJ(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidCNPJ("<%= c.propertyNatural %>"));
        }<%
            } else {
%>
        if (get<%= c.propertyNameUpper %>() != null && !Validator.isCNPJ(get<%= c.propertyNameUpper%>())) {
            throw new RespException(<%= c.ordinal_position %>, lang.isNotAValidCNPJ("<%= c.propertyNatural %>"));
        }<%
            }
        }
    }

    if (c.referencedTable && c.is_nullable === "NO") {
%>
        if (get<%= c.propertyNameUpper %>() == 0) {
            throw new RespException(<%= c.ordinal_position %>,  lang.cannotBeNull("<%= c.propertyNatural %>"));
        }<% 
    }
} 
%>    
    }

    public static <%= table.className %> buildAll(ResultSetWrapper rs, String alias) throws SQLException {
        <%= table.className %> <%= table.classLowerCamel %> = new <%= table.className %>();
<% 
for (var i in table.columns) { 
    var c = table.columns[i]; 
    if (c.smartType != "password") {
%>
        <%= table.classLowerCamel %>.set<%= c.propertyNameUpper %>(rs.<%= c.resultSetGetter %>(alias, "<%= c.column_name %>"));<% 
    }
} 
%>
        return <%= table.classLowerCamel %>;
    }
    
    public static <%= table.className %> buildAll(ResultSetWrapper rs) throws SQLException {
        return buildAll(rs, "<%= table.name %>");
    }
    
<% 
for (var j in table.columns) { 
    var cx = table.columns[j]; 
    if (!cx.referencedTable) {
%>
    public <%= cx.javaType %> get<%= cx.propertyNameUpper %>() {
        return <%= cx.propertyName %>;
    }

    public void set<%= cx.propertyNameUpper %>(<%= cx.javaType %> <%= cx.propertyName %>) {
        this.<%= cx.propertyName %> = <%= cx.propertyName %>;
    }
<% 
    } else {
%>
    public <%= cx.referencedTable.className %> get<%= cx.notIdPropertyNameUpper %>() {
        return <%= cx.notIdPropertyName %>;
    }

    public void set<%= cx.notIdPropertyNameUpper %>(<%= cx.referencedTable.className %> <%= cx.notIdPropertyName %>) {
        this.<%= cx.notIdPropertyName %> = <%= cx.notIdPropertyName %>;
    }

<% 
        if (cx.is_nullable === "YES") { 
%>
    public <%= cx.javaType %> get<%= cx.propertyNameUpper %>() {
        return <%= cx.notIdPropertyName %> == null || <%= cx.notIdPropertyName %>.get<%= cx.referencedTable.primaryColumns[0].propertyNameUpper %>() == 0 ? null : <%= cx.notIdPropertyName %>.get<%= cx.referencedTable.primaryColumns[0].propertyNameUpper %>();
    }

    public void set<%= cx.propertyNameUpper %>(<%= cx.javaType %> <%= cx.propertyName %>) {
        if (<%= cx.propertyName %> == null) {
            <%= cx.notIdPropertyName %> = null;
            return;
        }

        if (<%= cx.notIdPropertyName %> == null) {
            <%= cx.notIdPropertyName %> = new <%= cx.referencedTable.className %>();
        }
        
        <%= cx.notIdPropertyName %>.set<%= cx.referencedTable.primaryColumns[0].propertyNameUpper %>(<%= cx.propertyName %>);
    }
<% 
        } else { 
%>
    public <%= cx.javaType %> get<%= cx.propertyNameUpper %>() {
        return <%= cx.notIdPropertyName %> == null ? 0 : <%= cx.notIdPropertyName %>.get<%= cx.referencedTable.primaryColumns[0].propertyNameUpper %>();
    }

    public void set<%= cx.propertyNameUpper %>(<%= cx.javaType %> <%= cx.propertyName %>) {
        if (<%= cx.notIdPropertyName %> == null) {
            <%= cx.notIdPropertyName %> = new <%= cx.referencedTable.className %>();
        }
        
        <%= cx.notIdPropertyName %>.set<%= cx.referencedTable.primaryColumns[0].propertyNameUpper %>(<%= cx.propertyName %>);
    }
<% 
        } 
    } 
} 
%>
    
<% 
for (var j in table.NtoNcolumns) { 
    var cx = table.NtoNcolumns[j]; 
%>
    public List<<%= cx.otherTable.className %>> get<%= cx.NtoNtable.className %>() {
        return <%= cx.NtoNtable.classLowerCamel %>;
    }

    public void set<%= cx.NtoNtable.className %>(List<<%= cx.otherTable.className %>> <%= cx.NtoNtable.classLowerCamel %>) {
        this.<%= cx.NtoNtable.classLowerCamel %> = <%= cx.NtoNtable.classLowerCamel %>;
    }
<% 
} 
%>
    
}
