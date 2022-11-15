<#include "security.ftlh">
<#macro login path isRegisterForm>
    <#if !isAuth>
        <form action="${path}" method="post">
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label"> User Name : </label>
                <div class="col-sm-3">
                    <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                           class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           placeholder="User name"/>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </div>
            <#if isRegisterForm>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label"> Email: </label>
                    <div class="col-sm-3">
                        <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                               value="<#if user??>${user.email}</#if>" placeholder="Example@some.com"/>
                        <#if emailError??>
                            <div class="invalid-feedback">
                                ${emailError}
                            </div>
                        </#if>
                    </div>
                </div>
            </#if>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label"> Password: </label>
                <div class="col-sm-3">
                    <input type="password" name="password"
                           class="form-control ${(passwordError??)?string('is-invalid', '')}"
                           placeholder="Password"/>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>
            <#if isRegisterForm>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label"> Password confirmation: </label>
                    <div class="col-sm-3">
                        <input type="password" name="password_confirmation"
                               class="form-control ${(password_confirmationError??)?string('is-invalid', '')}"
                               placeholder="Password Confirmation"/>
                        <#if password_confirmationError??>
                            <div class="invalid-feedback">
                                ${password_confirmationError}
                            </div>
                        </#if>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="g-recaptcha" data-sitekey="6Ldp_AojAAAAAKKvhSLOD6RGPLgOXhefn6tRq5mT"></div>
                    <#if captchaError??>
                        <div class="alert alert-danger" role="alert">
                            ${captchaError}
                        </div>
                    </#if>
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if !isRegisterForm>
                <div>
                    <a href="/registration">Register</a>
                </div>
            </#if>
            <button type="submit" class="btn btn-primary"><#if isRegisterForm>Sing Up<#else> Sign In</#if></button>
        </form>
    <#else >
        <div>You're already logged in.</div>
    </#if>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>
