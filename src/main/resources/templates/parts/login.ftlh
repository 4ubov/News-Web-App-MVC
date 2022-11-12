<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm>
            <div>
                <a href="/registration">Register</a>
            </div>
        </#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Sing Up<#else> Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit" >Sign Out</button>
    </form>
</#macro>