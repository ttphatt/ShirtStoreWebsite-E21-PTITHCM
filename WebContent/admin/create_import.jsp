<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<head>
  <jsp:include page="pagehead.jsp"></jsp:include>
  <jsp:include page="pageLoad.jsp"/>
  <jsp:include page="add_product.jsp"></jsp:include>

  <link href="../css/warehouse-style.css" rel="stylesheet" type="text/css" />
  <link href="../css/modal-style.css" rel="stylesheet" type="text/css" />

</head>

<body class="body">
  <jsp:directive.include file="header.jsp" />
  <br><br><br><br>

  <div class="w-layout-blockcontainer w-container">
    <div class="nav-wrapper">

      <h1 class="heading-h1">Warehouse</h1>

      <div class="nav-button-wrapper">
        <a href="view_import" class="button w-button">View Import Receipt</a>
        <a href="#" class="button w-button">Create Import Receipt</a>
        <a href="stock_check" class="button w-button">Stock Check</a>
      </div>

      <h1 class="heading-h2">Create Import Receipt</h1>

    </div>

    <div class="form-wrapper w-form">
      <form id="create_import_form" name="create_import_form" method="post" class="form" action="add_import">
        <div class="fields-wrapper">
          <label for="staffName" class="field-label">Staff&#x27;s Name</label>
          <input class="text-field w-input" maxlength="256" name="staffName" type="text"
                 id="staffName" value="${sessionScope.userFullName}" readonly/>
        </div>

        <div class="fields-wrapper">
          <label for="importId" class="field-label">Import&#x27;s ID</label>
          <input class="text-field w-input" maxlength="256" name="importId"
                 type="text" id="importId"/>
        </div>

        <div class="fields-wrapper">
          <label for="totalPriceField" class="field-label">Total Price</label>
          <input class="text-field w-input" maxlength="256" name="totalPriceField" id="totalPriceField"
                  type="text" value="0.00" readonly/>
        </div>

        <div class="fields-wrapper is-contains-datefield">
          <label for="createdDate" class="field-label">Created Date</label>
          <input type="date" class="input" id="createdDate" name="createdDate"/>
        </div>

        <input type="hidden" name="productData" id="productData" />
        <input type="hidden" name="staffEmail" id="staffEmail" value="${sessionScope.userEmail}" />

      </form>
    </div>

    <table class="table-wrapper">
      <thead class="table_head">
        <tr class="table_row">
          <th class="table_header align-middle text-center">Index</th>
          <th class="table_header align-middle text-center">ID</th>
          <th class="table_header align-middle text-center">Product&#x27;s Name</th>
          <th class="table_header align-middle text-center">Size</th>
          <th class="table_header align-middle text-center">Quantity</th>
          <th class="table_header align-middle text-center">Price</th>
          <th class="table_header align-middle text-center">Total</th>
        </tr>
      </thead>
      <tbody class="table_body" id="result">

      </tbody>
    </table>

    <div class="button-group">
      <a href="#" class="button is-medium-button w-button" id="createImportBtn">Create</a>
      <a href="#" class="open-modal-btn button is-medium-button w-button">Add</a>
      <a href="#" class="button is-medium-button is-delete-button w-button"  id="deleteProductBtn">Detele</a>
    </div>

  </div>
  <script
          src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=671ef903248a1b1d233e0e01"
          type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
          crossorigin="anonymous">
  </script>

  <script src="../js/warehouse-js.js" type="text/javascript"></script>

  <br><br><br><br>
  <jsp:directive.include file="footer.jsp" />

  <script>
    // Get the modal
    var modal = document.getElementById("productModal");

    // Get the button that opens the modal
    var btn = document.querySelector(".open-modal-btn");

    // Get the <span> element that closes the modal
    var closeBtn = document.querySelectorAll(".close");

    // When the user clicks the button, open the modal
    btn.onclick = function () {
      modal.style.display = "block";
    }

    // When the user clicks on (x) or Cancel button, close the modal
    closeBtn.forEach(function (element) {
      element.onclick = function () {
        modal.style.display = "none";
      }
    });

    window.onclick = function (event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }

    let selectedRow = null; // Biến để lưu dòng được chọn

    // Sự kiện cho các dòng trong bảng, khi click vào sẽ chọn dòng
    document.getElementById('result').addEventListener('click', function(e) {
      if (e.target && e.target.nodeName === "TD") {
        // Bỏ chọn dòng trước đó
        if (selectedRow) {
          selectedRow.classList.remove('selected');
        }

        // Gán dòng được chọn
        selectedRow = e.target.closest('tr');
        selectedRow.classList.add('selected'); // Thêm class để tô sáng dòng
      }
    });

    // Khi nhấn nút 'Delete', xóa dòng đã chọn
    document.getElementById("deleteProductBtn").addEventListener("click", function() {
      if (selectedRow) {
        let rowTotal = parseFloat(selectedRow.querySelector('td:nth-child(6)').textContent); // Lấy tổng tiền của dòng được chọn
        selectedRow.remove(); // Xóa dòng được chọn
        updateTotalPrice(-rowTotal); // Cập nhật tổng tiền
        selectedRow = null; // Reset lại biến sau khi xóa dòng
      } else {
        alert("Please select a row to delete.");
      }
    });

    // Khi ấn nút 'Create'
    document.getElementById("createImportBtn").addEventListener("click", function(event) {
      const importId = document.getElementById("importId").value;
      const productsTable = document.getElementById("result");

      // Kiểm tra dữ liệu nhập
      if (importId === "" || productsTable.rows.length === 0) {
        alert("Please fill in all required fields and add at least one product.");
      } else {
        // Lấy dữ liệu từ bảng và chuyển vào input ẩn
        let productDataArray = [];
        for (let i = 0; i < productsTable.rows.length; i++) {
          const row = productsTable.rows[i];
          const productId = row.cells[1].textContent;
          const size = row.cells[3].textContent;
          const quantity = row.cells[4].textContent;
          const price = row.cells[5].textContent;

          // Thêm dữ liệu sản phẩm vào mảng dưới dạng JSON
          productDataArray.push({
            import_id: importId,
            product_id: productId,
            size: size,
            quantity: quantity,
            price: price,
          });
        }

        // Chuyển mảng dữ liệu sản phẩm thành chuỗi JSON và đặt vào input ẩn
        document.getElementById("productData").value = JSON.stringify(productDataArray);

        // Gửi form về server
        document.getElementById("create_import_form").submit();
      }
    });

  </script>
  <script>
    window.addEventListener("load", () => {
      const loader = document.querySelector(".loader_wrapper");

      setTimeout(() => {
        loader.classList.add("loader-hidden");

        loader.addEventListener("transitionend", () => {
          document.body.removeChild(loader);
        });
      }, 500);
    });
  </script>

</body>

</html>