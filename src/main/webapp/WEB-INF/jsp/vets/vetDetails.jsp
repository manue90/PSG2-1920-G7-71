<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">

    <h2>Vet Information</h2>

    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${vet.firstName} ${vet.lastName}"/></b></td>
        </tr>
    </table>
    
    <br/>
    <br/>
    <br/>
    <h2>Specialties</h2>

    <table class="table table-striped">
        <c:forEach var="specialties" items="${vet.specialties}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${specialties.name}"/></dd>
                    </dl>
                </td>
                
                <td valign="top">
                    <table class="table-condensed">
                    	<tr>
                    		<td>
                    			<spring:url value="/vets/{vetId}/specialty/{specialtyId}/edit" var="specialtyUrl">
                                    <spring:param name="vetId" value="${vet.id}"/>
                                    <spring:param name="specialtyId" value="${specialties.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(specialtyUrl)}">Edit Specialty</a>
                    		</td>
                    	</tr>
                   	</table>
                </td>
            </tr>
            
            
            
         </c:forEach>
      </table>

    <spring:url value="{vetId}/edit" var="editUrl">
        <spring:param name="vetId" value="${vet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Vet</a>
    
    <spring:url value="{vetId}/specialty/new" var="addUrl">
        <spring:param name="vetId" value="${vet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Specialty</a>
   
</petclinic:layout>
