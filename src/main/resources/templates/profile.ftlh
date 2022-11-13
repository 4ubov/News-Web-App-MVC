<#import "parts/common.ftlh" as c>
<@c.page>
    <h6>Username: ${username}</h6>
    <h6>Email: ${email}</h6>
    ${message!}
    <form method="post">
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label"> Change Email: </label>
                <div class="col-sm-3">
                    <input type="email" name="email" class="form-control" placeholder="New Email" value="${email!''}"/>
                </div>
            </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Change Password: </label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="New Password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>
