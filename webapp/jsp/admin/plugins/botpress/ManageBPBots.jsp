<jsp:useBean id="managebotpressbotsBPBot" scope="session" class="fr.paris.lutece.plugins.botpress.web.BPBotJspBean" />
<% String strContent = managebotpressbotsBPBot.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
