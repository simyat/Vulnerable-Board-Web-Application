<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>HackTheBox</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous" />
    </head>
    <body class="bg-dark">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h2 class="text-light my-5">HACK THE BOX</h2>
                    <p class="text-light">회원님, 안녕하세요. HACK THE BOX는 로그인한 회원님만 사용 가능한 서비스입니다. 로그인을 하시면 더 많은 서비스를 경험하실 수 있습니다.</p>
                    <button type="button" class="btn btn-light my-3" data-toggle="modal" data-target="#loginModal">로그인</button>
                    <button type="button" class="btn btn-light my-3" onclick="location.href='/hackthebox/api/signup.jsp'">회원가입</button>
                    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered d-flex align-items-center" role="document" style="margin-top: -13%">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="loginModalLabel">HACK THE BOX</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form id="login-form">
                                        <div class="form-group">
                                            <input type="text" class="form-control" placeholder="아이디" name="user_id" id="username" required />
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control" placeholder="비밀번호" name="user_pw" id="password" required />
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary" id="login-button">로그인</button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="<%= request.getContextPath() %>/js/login.js" charset="utf-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
    </body>
</html>
