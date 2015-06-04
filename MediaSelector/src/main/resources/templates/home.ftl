<#if user ??>
<div class="jumbotron">
    <div class="row">
        <div class="jumbotron-header col-md-8 col-md-offset-2">
            <h2>Select a subscribed media rater</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
        <#assign size = medias?size>
        <#if size == 0>
            <p style="text-align: center">You don't have any subscription</p>
        <#elseif size == 1>
            <div class="row" id="media-row">
                <#list medias as media>
                    <div class="col-md-6 col-md-offset-3">
                        <a href="/media/${media.id}">
                            <button type="button" class="btn btn-default media-tb panel panel-default" style="width:100%">
                                <div class="panel-body">
                                    <h2>${media.title}</h2>
                                    <p style="white-space: normal; overflow: hidden">${media.description}</p>
                                </div>
                            </button>
                        </a>
                    </div>
                </#list>
            </div>
        <#elseif size == 2>
            <div class="row" id="media-row">
                <#list medias as media>
                    <div class="col-md-6">
                        <a href="/media/${media.id}">
                            <button type="button" class="btn btn-default media-tb panel panel-default" style="width:100%">
                                <div class="panel-body">
                                    <h2>${media.title}</h2>
                                    <p style="white-space: normal; overflow: hidden">${media.description}</p>
                                </div>
                            </button>
                        </a>
                    </div>
                </#list>
            </div>
        <#elseif size == 3>
            <div class="row" id="media-row">
                <#list medias as media>
                    <div class="col-lg-4">
                        <a href="/media/${media.id}">
                            <button type="button" class="btn btn-default media-tb panel panel-default" style="width:100%">
                                <div class="panel-body">
                                    <h2>${media.title}</h2>
                                    <p style="white-space: normal; overflow: hidden">${media.description}</p>
                                </div>
                            </button>
                        </a>
                    </div>
                </#list>
            </div>
        <#elseif size == 4>
            <div class="row" id="media-row">
            <#list medias as media>
                <div class="col-md-6 col-lg-3">
                    <a href="/media/${media.id}">
                        <button type="button" class="btn btn-default media-tb panel panel-default" style="width:100%">
                            <div class="panel-body">
                                <h2>${media.title}</h2>
                                <p style="white-space: normal; overflow: hidden">${media.description}</p>
                            </div>
                        </button>
                    </a>
                </div>
            </#list>
            </div>
        </#if>
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <button id="more-button" type="button" class="btn btn-default btn-lg">
                        <a href="/media/search">
                            <span class="glyphicon glyphicon-plus-sign"></span> Select other medias
                        </a>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2>Create your media rater!</h2>
            <p>Fill the media rater creation form, we deal with the rest</p>
            <a class="btn btn-primary btn-md" href="/media/create" role="button">Create your media rater &raquo;</a>
        </div>
        <div class="col-md-6">
            <h2>Select an existing media rater</h2>
            <p>Want to rate some media resources? Start by choosing an existing media rater</p>
            <a class="btn btn-primary btn-md" href="/media/search" role="button">Search a media rater &raquo;</a>
        </div>
    </div>
</div>
<#else>
<div class="jumbotron">
    <div class="container">
        <h1>Welcome to Media Selector!</h1>
        <p>Create your own media rater, based on existing data stored in RDF dataset, or rate media items of an existing media rater! After a
            consequent count of ratings, you could try the media selector that select a media item that you should like, or the media predictor,
            that predicts whether or not you might like the selected media item!
        </p>
        <p>
            <a class="btn btn-primary btn-lg" href="/user/register" role="button">Start and register &raquo;</a>
            <a class="btn btn-primary btn-lg" href="/user/connect" role="button">Log in &raquo;</a>
        </p>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2>Create your media rater!</h2>
            <p>Fill the media rater creation form, we deal with the rest</p>
            <a class="btn btn-primary btn-md" href="/media/create" role="button">Create your media rater &raquo;</a>
        </div>
        <div class="col-md-6">
            <h2>Select an existing media rater</h2>
            <p>Want to rate some media resources? Start by choosing an existing media rater</p>
            <a class="btn btn-primary btn-md" href="/media/search" role="button">Search a media rater &raquo;</a>
        </div>
    </div>
</div>
</#if>