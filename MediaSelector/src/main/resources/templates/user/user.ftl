<div class="container">
    <div class="page-header">
        <h1>${user_target.login} <small>(ID: ${user_target.id})</small></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Owned Medias Raters</h3>
        </div>
        <div class="panel-body">
            <ul class="list-group" id="media-list">
            <#list medias as media>
                <li class="list-group-item">
                    <span class="media-id" style="display: none">${media.id}</span>
                    <a href="/media/${media.id}" style="font-weight: bold; font-size: 1.2em">${media.title}</a>
                    <span>
                        <span class="badge" style="margin-left: 10px;">
                            <span class="glyphicon glyphicon-heart"></span> ${media.subscribedCount} subscriber(s)
                        </span>
                    </span>
                    <p>${media.description}</p>
                </li>
            </#list>
            </ul>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Subscriptions</h3>
        </div>
        <div class="panel-body">
            <ul class="list-group" id="subscription-list">
            <#list subscriptions as subscription>
                <li class="list-group-item">
                    <span class="media-id" style="display: none">${subscription.id}</span>
                    <a href="/media/${subscription.id}" style="font-weight: bold; font-size: 1.2em">${subscription.title}</a>
                    <span>
                        <span class="badge" style="margin-left: 10px;">
                            <span class="glyphicon glyphicon-heart"></span> ${subscription.subscribedCount} subscriber(s)
                        </span>
                    </span>
                    <button class="unsubscribe-button btn btn-danger pull-right">Unsubscribe</button>
                    <p>${subscription.description}</p>
                </li>
            </#list>
            </ul>
        </div>
    </div>
</div>