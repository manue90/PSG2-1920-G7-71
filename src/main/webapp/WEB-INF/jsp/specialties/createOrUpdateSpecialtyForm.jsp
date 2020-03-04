<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vets">
    <h2>
        <c:if test="${specialty['new']}">New </c:if> Specialty
    </h2>
    
  
    <form:form modelAttribute="specialty" class="form-horizontal" id="add-vet-form">
        <div class="form-group has-feedback">
			<div>
				<div class="form-group">
                    <label class="col-sm-2 control-label">Vet</label>
                    <div class="col-sm-10">
                        <c:out value="${vet.firstName} ${vet.lastName}"/>
                    </div>
                </div>
				<petclinic:selectField label="Specialty" name="name" size="3" names="${spec}" />
			</div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${specialty['new']}">
                        <button class="btn btn-default" type="submit">Add Specialty</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Specialty</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
  
</petclinic:layout>
