<link rel="stylesheet" type="text/css" href="css/card_template.css"/>
<div class="col text-center" style="font-family: 'Roboto', sans-serif">
	<div class="card">
		<a href="view_shirt?id=${shirt.shirtId}">
			<div class="content">
				<img class="image-product" src="${shirt.shirtImage}"/>
			</div>
		</a>

		<a href="view_shirt?id=${shirt.shirtId}" style="text-decoration: none">
			<div class="content">
				<div style="font-size: 25px">
					<b style="color: #FFFFFF">${shirt.shirtName}</b>
				</div>
			</div>
		</a>

		<a href="view_shirt?id=${shirt.shirtId}" style="text-decoration: none">
			<div class="content">
				<jsp:directive.include file="shirt_rating.jsp"/>
			</div>
		</a>

		<a href="view_shirt?id=${shirt.shirtId}" style="text-decoration: none">
			<div class="content" style="font-size: 25px; color: #FFFFFF">
				<b>From: ${shirt.brand}</b>
			</div>
		</a>

		<a href="view_shirt?id=${shirt.shirtId}" style="text-decoration: none">
			<div class="content" style="font-size: 25px; color: #FFFFFF">
				<b>Price: $${shirt.shirtPrice}</b>
			</div>
		</a>
	</div>
</div>
