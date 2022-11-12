<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="row row-cols-lg-auto g-2 align-items-center">
                <div class="col-12">
                    <input type="text" name="filter" class="form-control" placeholder="Введите фильтр"
                           value="${filter!}">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Найти</button>
                </div>
            </form>
        </div>
    </div>

    <a class="btn btn-primary mt-3" data-bs-toggle="collapse" href="#collapseNewMessage" role="button"
       aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>

    <div class="collapse" id="collapseNewMessage">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group mt-3">
                    <input type="text" name="text" class="form-control" placeholder="Введите сообщение"/>
                </div>
                <div class="form-group mt-3">
                    <input type="text" name="tag" class="form-control" placeholder="Тэг">
                </div>
                <div class="form-group mt-3">
                    <input type="file" name="file" class="form-control">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group my-3">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
    <#list messages as message>
        <div class="col-sm-4 card my-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
            <div class="m-2">
            <span>${message.text}</span>
            <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    <#else>
        No message
    </#list>
    </div>
</@c.page>