<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <title>Rate our shirt</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
    
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            padding-top: 70px;
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <div class="container mt-5">
        <div class="row justify-content-center text-center">
            <form id="rateForm" action="submit_rate" method="post" class="col-lg-8">
                <div class="card">
                    <div class="card-body">
                        <h2>Share us your thoughts, ${loggedCustomer.fullName}</h2>
                        <hr>
                        <div class="row">
                            <div class="col-md-4">
                                <img src="${shirt.shirtImage}" class="img-fluid mb-3" alt="${shirt.shirtName}">
                                <h2><b>${shirt.shirtName}</b></h2>
                            </div>
                            <div class="col-md-8">
                                <div id="rateYo"></div>
                                <input type="hidden" id="rating" name="rating"/>
                                <input type="hidden" name="shirtId" value="${shirt.shirtId}"/>
                                <div class="form-group mt-3">
                                    <input type="text" id="headline" name="headline" class="form-control" required placeholder="Headline (Required)">
                                </div>
                                <div class="form-group mt-3">
                                    <textarea id="ratingDetail" name="ratingDetail" class="form-control" rows="5" required placeholder="Share us your experience"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end mt-4">
                            <button id="submit" type="submit" class="btn btn-outline-success me-2">Submit</button>
                            <button type="button" class="btn btn-outline-info" onclick="history.go(-1);">Cancel</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    
    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        // $("#rateForm").validate({
        //     rules: {
        //         headline: "required",
        //         ratingDetail: "required",
        //     },
        //     messages: {
        //         headline: "Please enter the headline",
        //         ratingDetail: "Please enter the detail of your rating",
        //     }
        // });

        var ratingChosen = false;
        var headline = ""
        var ratingDetail = ""
        console.log(headline, ratingDetail)

        $("#rateYo").rateYo({
            starWidth: "40px",
            fullStar: true,
            ratedFill: "#000000",
            onSet: function(rating, rateYoInstance){
                $("#rating").val(rating)
                ratingChosen = true
            }
        });

        $("#submit").click(function(event) {
            headline = document.getElementById("headline").value;
            ratingDetail = document.getElementById("ratingDetail").value

            if (!ratingChosen) {
                Swal.fire(getMessageContent("NOT_NULL_RATING_STARS"))
                event.preventDefault()
            }

            else if(headline.length === 0){
                getMessageContent("NOT_NULL_HEADLINE", event);
            }

            else if(ratingDetail.length === 0){
                getMessageContent("NOT_NULL_RATING_DETAIL", event);
            }
        });

        function getMessageContent(messageId, event){
            fetch('csvdata?id=' + messageId)
                .then(response => response.json())
                .then(data =>{
                    if(data.message){
                        Swal.fire(data.message);
                        event.preventDefault();
                    }
                    else{
                        Swal.fire("Message not found");
                        event.preventDefault();
                    }
                })
                .catch(error => console.error("Error: ", error));
        }

        $("#buttonCancel").click(function(){
            history.go(-1);
        });
    });
</script>
</html>
