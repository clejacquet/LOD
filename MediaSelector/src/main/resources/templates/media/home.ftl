<div class="container">
    <div id="media-id" style="display: none;">${media.id}</div>
    <div class="page-header">
        <div class="pull-right">
        <#if admin == true>
            <button id="delete-button" class="btn btn-danger">Delete</button>
        </#if>
        <#if media.subscribed>
            <button id="unsubscribe-button" class="btn btn-danger">Unsubscribe</button>
        <#else>
            <button id="subscribe-button" class="btn btn-primary">Subscribe</button>
        </#if>
        </div>
        <h1>${media.title}<small> - <a href="/user/${media.authorId}">${media.author}</a></small></h1>
    </div>
    <p style="font-size: 1.2em; margin-bottom: 20px;">
        ${media.description}
    </p>
    <div class="well">
        <h3 style="text-align: center; margin-bottom: 20px;">Search a resource</h3>
        <div class="row" style="margin-bottom: 15px;">
            <div class="col-md-8">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">Resource Name</span>
                    <input type="text" id="search-input" class="form-control" placeholder="Resource name" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="col-md-2">
                <select class="form-control" id="language">
                    <option>All</option>
                    <option>None</option>
                    <option>French</option>
                    <option>English</option>
                    <option>Japanese</option>
                    <option>Spanish</option>
                </select>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-default" id="search-button">Search!</button>
            </div>
        </div>
        <div id="search-div" class="panel panel-default">
            <div class="panel-heading">
                <button type="button" id="hide-search-results" class="close">
                    <span>&times;</span>
                </button>
                <h3 class="panel-title">Search Result</h3>
            </div>
            <div class="panel-body">
                <ul class="list-group" id="search-results">
                </ul>
            </div>
        </div>
    </div>
    <div class="well">
        <h3 style="text-align: center; margin-bottom: 20px;">Selector</h3>
        <button type="button" class="btn btn-default" id="selector-button" style="margin: auto; display: block; margin-bottom: 20px">Launch the selection!</button>
        <div id="results-div" class="panel panel-default">
            <div class="panel-heading">
                <button type="button" id="hide-selector-results" class="close">
                    <span>&times;</span>
                </button>
                <h3 class="panel-title">Results</h3>
            </div>
            <div class="panel-body">
                <ul class="list-group" id="results">
                </ul>
            </div>
        </div>
    </div>
</div>
