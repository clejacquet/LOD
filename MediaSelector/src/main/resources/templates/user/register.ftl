<div class="page-header">
    <h1>Media Selector <small>- Register</small></h1>
</div>

<form method="POST" action="/user/register">
    <input type="hidden" name="ask" value="register" />
    <div class="input-group">
        <span class="input-group-addon">Login</span>
        <input name="login" type="text" class="form-control" placeholder="Login">
    </div>
    <div class="input-group">
        <span class="input-group-addon">Password</span>
        <input name="password" type="password" class="form-control" placeholder="Password">
    </div>
    <span class="input-group-btn">
        <input type="submit" class="btn btn-default" value="Register"/>
    </span>
</form>