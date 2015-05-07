<div class="page-header">
    <h1>Media Selector <small>- Log In</small></h1>
</div>

<form method="POST" action="/${artifact_name}/user/login">
    <input type="hidden" name="ask" value="login" />
    <div class="input-group">
        <span class="input-group-addon">Login</span>
        <input name="login" type="text" class="form-control" placeholder="Login">
    </div>
    <div class="input-group">
        <span class="input-group-addon">Password</span>
        <input name="password" type="password" class="form-control" placeholder="Password">
    </div>
    <span class="input-group-btn">
        <input type="submit" class="btn btn-default" value="Log in"/>
    </span>
</form>