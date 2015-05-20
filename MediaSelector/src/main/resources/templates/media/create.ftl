<div class="container">
    <div class="row">
        <div class="col-lg-12 well">
            <form>
                <legend>Media Rater Creation Form</legend>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">General Properties</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="input-group">
                                    <span class="input-group-addon">Title</span>
                                    <input type="text" id="media-name" class="form-control" placeholder="Add a name here">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Dataset Access</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="input-group">
                                    <span class="input-group-addon">SPARQL Endpoint</span>
                                    <input type="text" id="media-sparql" class="form-control" placeholder="Add the SPARQL endpoint URL of your dataset here">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Media Resource</h3>
                    </div>
                    <div class="panel-body">
                        <div id="media-resource">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Name</span>
                                        <input type="text" class="form-control resource-name" placeholder="Add a resource name here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Title Property</span>
                                        <input type="text" class="form-control resource-title-property" placeholder="Add the title property IRI here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Type IRI</span>
                                        <input type="text" class="form-control resource-type" placeholder="Add the rdf:type IRI of the resource here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Author Property</span>
                                        <input type="text" id="author-property" class="form-control" placeholder="Add the author property here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Author Name Property</span>
                                        <input type="text" id="author-name-property" class="form-control" placeholder="Add a resource name here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Abstract Property</span>
                                        <input type="text" id="abstract-property" class="form-control" placeholder="Add the abstract property here">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon">Date Property</span>
                                        <input type="text" id="date-property" class="form-control" placeholder="Add the date property here">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Related Resources</h3>
                    </div>
                    <div class="panel-body">
                        <ul id="related-resources">
                            <li id="resources-row-template" style="display: none; margin-bottom: 20px;">
                                <div class="row">
                                    <div class="col-sm-11">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon">Name</span>
                                                    <input type="text" class="form-control resource-name" placeholder="Add a resource name here">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon">Type</span>
                                                    <input type="text" class="form-control resource-type" placeholder="Add the rdf:type IRI of the resource here">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon">Property</span>
                                                    <input type="text" class="form-control resource-property" placeholder="Add the property IRI with the media resource here">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon">Title Property</span>
                                                    <input type="text" class="form-control resource-title-property" placeholder="Add the title property IRI here">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-1">
                                        <button type="button" class="btn btn-default remove-resource" aria-label="Delete Resource">
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <button type="button" class="btn btn-default add-resource" aria-label="Add Resource">
                            <span class="glyphicon glyphicon-plus"></span> More
                        </button>
                    </div>
                </div>
                <button id="resources-submit" class="btn btn-primary">Submit</button>
            </form>
            </div>
    </div>
</div>