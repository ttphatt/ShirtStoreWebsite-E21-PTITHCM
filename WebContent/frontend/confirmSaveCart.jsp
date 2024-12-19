<html>
  <head>
      <title>Confirm Save Cart form</title>

      <style>
        .confirm-div {
          background-color: #ccc;
        }

        .green-btn {
          background-color: #47a04b;
        }

        .green-btn:hover {
          background-color: #368339;
        }

        .green-btn:active {
          background-color: #2d6830;
        }

        .green-btn:disabled {
          background-color: #c8eac9;
          color: #1b7a43;
        }

        .red-btn {
          background-color: #f93a3a;
        }

        .red-btn:hover {
          background-color: #e71f1f;
        }

        .red-btn:active {
          background-color: #c21313;
        }

        .red-btn:disabled {
          background-color: #ffc7c7;
          color: #c21313;
        }

        .confirm-div {
          font-size: 14px;
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          width: 500px;
          padding: 20px;
          text-align: center;
          border-radius: 6px;
          box-shadow: 4px 4px 15px #3e3e3e;
          font-family: "Segoe UI", Tahoma, sans-serif;
          cursor: default;
        }

        .confirm-div button {
          cursor: pointer;
          width: 100%;
          padding: 4px 6px;
          border-radius: 4px;
          color: #fff;
          border: none;
          height: 30px;
          width: 100%;
        }

        .confirm-div p {
          display: flex;
          flex-direction: column;
        }

        .confirm-div p strong {
          margin-bottom: 15px;
        }

        .warning-general {
          position: absolute; /* switch to fixed */
          top: 0;
          left: 0;
          z-index: 999;
          width: 100%;
          height: 100%;
          backdrop-filter: blur(2px);
        }

        .modals-container {
          display: flex;
          flex-direction: row;
          height: 32px;
          margin-top: 20px;
          gap: 7px;
          width: 100%;
        }

      </style>
  </head>
  <body>
    <div class="warning-general">
      <div class="confirm-div">
        <p>
          <strong>Wait a minute</strong>
          <strong>Do you want to save current cart to your cart ?</strong>
        </p>

        <form method="post" action="handleSaveCart">
          <div class="modals-container">
            <input type="hidden" name="customerId" value="${customerId}">
            <button type="submit" name="action" value="cancel" class="red-btn">No</button>
            <button type="submit" name="action" value="save" class="green-btn">Yes</button>
          </div>
        </form>

      </div>
    </div>
  </body>

</html>