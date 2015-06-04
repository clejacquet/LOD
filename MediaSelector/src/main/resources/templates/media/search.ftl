<div class="container">
    <div class="row" style="margin-bottom: 15px;">
        <div class="col-md-11">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Media Name</span>
                <input type="text" id="search-input" class="form-control" placeholder="Resource name" aria-describedby="basic-addon1">
            </div>
        </div>
        <div class="col-md-1">
            <div class="btn-group" role="group">
                <button type="button" class="btn btn-default" id="search-button">Search!</button>
            </div>
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
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Subscriptions</h3>
        </div>
        <div class="panel-body">
            <ul class="list-group" id="subscription-list">
            <#list medias as media>
                <li class="list-group-item">
                    <span class="media-id" style="display: none">${media.id}</span>
                    <a href="/media/${media.id}" style="font-weight: bold; font-size: 1.2em">${media.title}</a>
                    <span>
                        <span class="badge" style="margin-left: 10px;">
                            <span class="glyphicon glyphicon-heart"></span> ${media.subscribedCount} subscriber(s)
                        </span>
                    </span>
                    <button class="unsubscribe-button btn btn-danger pull-right">Unsubscribe</button>
                    <p>${media.description}</p>
                </li>
            </#list>
            </ul>
        </div>
    </div>
</div>