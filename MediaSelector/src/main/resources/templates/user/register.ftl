<div class="container">
    <form class="form-signin" method="POST" action="/user/register">
        <h2 class="form-signin-heading">Please register</h2>
        <input type="hidden" name="ask" value="register" />
        <label for="inputLogin" class="sr-only">Login</label>
        <input name="login" type="text" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
        <div style="text-align:center; margin-top: 5px; font-size:1.2em">
            Or <a id="login-link" href="#">Login</a>
        </div>
    </form>
</div>