<div class="container">
    <div class="row" style="margin-bottom: 15px;">
        <div class="col-md-8">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Resource Name</span>
                <input type="text" id="search-input" class="form-control" placeholder="Resource name" aria-describedby="basic-addon1">
            </div>
        </div>
        <div class="col-md-3">
            <select class="form-control" id="language">
                <option>All</option>
                <option>None</option>
                <option>French</option>
                <option>English</option>
                <option>Japanese</option>
                <option>Spanish</option>
            </select>
        </div>
        <div class="col-md-1">
            <div class="btn-group" role="group">
                <button type="button" class="btn btn-default" id="search-button">Search!</button>
            </div>
        </div>
    </div>
    <div id="search-div" class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Search Result</h3>
        </div>
        <div class="panel-body">
            <ul class="list-group" id="search-results">
            </ul>
        </div>
    </div>
</div>
