<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/taglibs.jsp"%>

<%@ include file="head.jsp"%>
			
	<c:forEach var="processId" items="${event.getObjectIds()}" varStatus="status">
		<u:sc>
	    	<c:set var="process" value="${processDao.getProcess(processId)}"/>
	    	<c:set var="contractLink" value="${u:getFirst(processLinkDao.getObjectLinksWithType(processId, 'contract%'))}"/>
	    	
	    	<c:if test="${not empty contractLink}">
		    	<u:newInstance var="billingDao" clazz="ru.bgcrm.plugin.bgbilling.proto.dao.ContractDAO">
					<u:param value="${ctxUser}"/>
					<u:param value="${fn:substringAfter(contractLink.linkedObjectType, ':')}"/>
				</u:newInstance>
				<c:set var="contractInfo" value="${billingDao.getContractInfo(contractLink.linkedObjectId)}"/>
			</c:if>
	    
	    	<h2 ${status.count ne 1 ? 'style="page-break-before: always;"' :''}>ООО «АтелРыбинск»</h2>		  	
		  	<%@ include file="act_include.jsp"%>
		  	
		  	<h2>ООО «АтелРыбинск»</h2>		  	
		  	<%@ include file="act_include.jsp"%>
		 </u:sc>		  	
   </c:forEach>
</body>
</html>