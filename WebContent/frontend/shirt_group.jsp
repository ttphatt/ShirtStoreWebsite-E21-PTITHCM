<div class="col text-center">
		<div>
			<a href="view_shirt?id=${shirt.shirtId}">
				<img src="${shirt.shirtImage}" width="250" height="240"/>
			</a>
		</div>
		<div style="font-size: 25px">
				<a href="view_shirt?id=${shirt.shirtId}" class="text-dark">
					<b>${shirt.shirtName}</b>
				</a>
		</div>
							
		<div>
			<jsp:directive.include file="shirt_rating.jsp"/>
		</div>
							
		<div>From: ${shirt.brand}</div>
		<div><b>Price: $${shirt.shirtPrice}</b></div>
</div>