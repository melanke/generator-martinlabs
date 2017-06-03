import Vue from 'vue';
import VueResource from 'vue-resource';
import AppRouter from './AppRouter';
import AppTranslator from './AppTranslator';
import simpleStorage from 'simpleStorage.js';
import AppBus from '../service/AppBus';

Vue.use(VueResource);

Vue.http.interceptors.push((request, next) => {
    //all requests pass by here

    request.headers.set("Accept-Language", AppTranslator.lang);
    request.headers.set("X-Client-Version", "w1.0.0"); //w = web
    
    var token = simpleStorage.get("token<%= modulenameUpper %>") || null;
    if (token) {
        request.headers.set('Authorization', 'Bearer ' + token);
    }

    next((resp) => {
        if (!resp.ok) {
            AppBus.$emit("alert", "danger", resp.body.message, 3000);
            
            if (resp.body.code === 33) {
                simpleStorage.deleteKey("token<%= modulenameUpper %>");
                simpleStorage.set("beforeLoginIntention", location.href);
                AppRouter.push("/login");
            }
        }
    });
});

export default new (function () {
    this.login = Vue.resource("../ws/<%= modulenameUpper %>/Login");<% 
    
    for (var i in tables) {
        var table = tables[i];
        if (table.inCrud && !table.isNtoNtable) {
    %>
    this.<%= table.classLowerCamel %> = Vue.resource("../ws/<%= modulenameUpper %>/<%= table.className %><% 
if (table.primaryColumns.length == 1) {
    %>{/id}<%
} else {
    for (var k in table.primaryColumns) {
        %>{/<%= table.primaryColumns[k].propertyName %>}<%
    } 
}
        %>");<%
        }
    }
    %>
})();