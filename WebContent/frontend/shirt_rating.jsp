<c:forTokens items="${shirt.ratingStars}" delims="," var="star">
	<c:if test="${star eq 'on'}">
		<img src="images/OnStarV1.png">
	</c:if>
							
	<c:if test="${star eq 'half'}">
		<img src="images/HalfStarV1.png">
	</c:if>
							
	<c:if test="${star eq 'off'}">
		<img src="images/OffStarV1.png">
	</c:if>
</c:forTokens>