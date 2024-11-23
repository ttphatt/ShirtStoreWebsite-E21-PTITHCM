<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body class="body">
<div id="productModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h1 class="Heading-H1">Add Product</h1>
        </div>

        <div class="form-wrapper is_add_form w-form">
            <form id="email-form" name="email-form" method="get" class="form" action="#">
                <!-- Product Select -->
                <div class="fields-wrapper is_contain_combobox">
                    <label for="productSelect" class="field-label">Product's Name</label>
                    <select id="productSelect" name="field" class="combobox w-select">
                        <option value="">Select a product...</option>
                        <c:forEach var="shirt" items="${shirtList}">
                            <option value="${shirt.shirtID}">${shirt.shirtName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="fields-wrapper is_contain_combobox">
                    <label for="sizeSelect" class="field-label">Size</label>
                    <select id="sizeSelect" name="field" class="combobox w-select">
                        <option value="">Select a size...</option>
                        <option value="S">S</option>
                        <option value="L">L</option>
                        <option value="M">M</option>
                        <option value="XL">XL</option>
                        <option value="XXL">XXL</option>
                    </select>
                </div>

                <!-- Product ID -->
                <div class="fields-wrapper">
                    <label for="productId" class="field-label">Product's ID</label>
                    <input class="text-field is-form_add_product w-input" name="productId" type="text" id="productId" readonly />
                </div>

                <!-- Quantity -->
                <div class="fields-wrapper">
                    <label for="quantity" class="field-label">Quantity</label>
                    <input class="text-field is-form_add_product w-input" name="quantity" type="text" id="quantity" />
                </div>

                <!-- Price -->
                <div class="fields-wrapper">
                    <label for="price" class="field-label">Price</label>
                    <input class="text-field is-form_add_product w-input" name="price" type="text" id="price" />
                </div>
            </form>
        </div>

        <!-- Buttons for Add and Cancel -->
        <div class="form_button">
            <a href="#" class="button is-medium-button w-button" id="addProductBtn">Add</a>
            <a href="#" class="button is-medium-button is-red w-button close">Cancel</a>
        </div>
    </div>
</div>

<script>
    const productSelect = document.getElementById("productSelect");
    const sizeSelect = document.getElementById("sizeSelect");
    const productIdField = document.getElementById("productId");
    const quantityField = document.getElementById("quantity");
    const priceField = document.getElementById("price");
    let counter = 1;

    // Update product ID when a product is selected
    productSelect.addEventListener("change", function() {
        const selectedProductId = productSelect.value;
        productIdField.value = selectedProductId;
    });

    // When 'Add' button is clicked
    document.getElementById("addProductBtn").addEventListener("click", function() {
        // Get form values
        let productName = productSelect.options[productSelect.selectedIndex].text;
        let size = sizeSelect.value;
        let productId = productIdField.value;
        let quantity = quantityField.value;
        let price = priceField.value;

        if (!productId || isNaN(parseInt(quantity)) || isNaN(parseInt(price)) || size === "") {
            alert("Please fill out valid information.");
            return;
        }

        const total = (quantity * price).toString();

        // Check if product already exists in the table
        let table = document.getElementById("result");
        let rows = table.getElementsByTagName("tr");
        let found = false;

        // Loop through the table rows to find the matching product ID
        for (let i = 0; i < rows.length; i++) {
            let cells = rows[i].getElementsByTagName("td");
            if (cells.length > 0 && cells[1].textContent === productId && cells[3].textContent === size) { // Check if productId matches
                // Update the existing row with new quantity, price, and total
                let existingQuantity = parseInt(cells[4].textContent);  // Existing quantity
                let existingPrice = parseFloat(cells[5].textContent);  // Existing price
                let newQuantity = existingQuantity + parseInt(quantity); // Add the new quantity to existing one
                let newTotal = newQuantity * price; // Recalculate total using the old price
                let oldTotal = existingPrice * existingQuantity;

                cells[5].textContent = price;
                cells[4].textContent = newQuantity;  // Update quantity
                cells[6].textContent = newTotal.toFixed(2);  // Update total

                // Update the total price field
                updateTotalPrice((newTotal - oldTotal).toFixed(2));

                found = true; // Product found and updated
                break;
            }
        }

        // If the product wasn't found, create a new row
        if (!found) {
            let parent = document.createElement('tr');
            parent.classList.add('table_row');

            let indexCol = document.createElement('td');
            indexCol.classList.add('table_cell', 'align-middle', 'text-center');
            let IDCol = document.createElement('td');
            IDCol.classList.add('table_cell', 'align-middle', 'text-center');
            let nameCol = document.createElement('td');
            nameCol.classList.add('table_cell', 'align-middle', 'text-center');
            let sizeCol = document.createElement('td');
            sizeCol.classList.add('table_cell', 'align-middle', 'text-center');
            let quanCol = document.createElement('td');
            quanCol.classList.add('table_cell', 'align-middle', 'text-center');
            let priceCol = document.createElement('td');
            priceCol.classList.add('table_cell', 'align-middle', 'text-center');
            let totalCol = document.createElement('td');
            totalCol.classList.add('table_cell', 'align-middle', 'text-center');

            let indexText = document.createTextNode(counter.toString());
            let idText = document.createTextNode(productId);
            let nameText = document.createTextNode(productName);
            let sizeText = document.createTextNode(size);
            let quanText = document.createTextNode(quantity);
            let priceText = document.createTextNode(price);
            let totalText = document.createTextNode(total);

            indexCol.appendChild(indexText);
            IDCol.appendChild(idText);
            nameCol.appendChild(nameText);
            sizeCol.appendChild(sizeText);
            quanCol.appendChild(quanText);
            priceCol.appendChild(priceText);
            totalCol.appendChild(totalText);

            parent.appendChild(indexCol);
            parent.appendChild(IDCol);
            parent.appendChild(nameCol);
            parent.appendChild(sizeCol);
            parent.appendChild(quanCol);
            parent.appendChild(priceCol);
            parent.appendChild(totalCol);

            document.getElementById('result').appendChild(parent);

            updateTotalPrice(total);
            counter++;
        }

        // Close modal
        document.getElementById("productModal").style.display = "none";

        // Clear modal fields for next input
        productSelect.value = "";
        sizeSelect.value = "";
        productIdField.value = "";
        quantityField.value = "";
        priceField.value = "";
    });

    // Function to update total price
    function updateTotalPrice(newTotal) {
        const totalPriceField = document.getElementById("totalPriceField");
        let currentTotal = parseFloat(totalPriceField.value) || 0;
        currentTotal += parseFloat(newTotal);
        totalPriceField.value = currentTotal.toFixed(2);
    }
</script>
</body>
