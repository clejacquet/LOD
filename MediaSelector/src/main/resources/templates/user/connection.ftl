<div class="container">
    <form class="form-signin" method="POST" action="/user/connect">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="hidden" name="ask" value="connection" />
        <label for="inputLogin" class="sr-only">Login</label>
        <input name="login" type="text" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>