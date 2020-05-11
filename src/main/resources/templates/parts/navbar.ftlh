<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Умная Парковка</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/parking">Парковки</a>
            </li>
            <#if isAdmin || isManager>
            <li class="nav-item">
                <a class="nav-link" href="/user">Список пользователей</a>
            </li>
            </#if>
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Профиль</a>
                </li>
            </#if>
            <#if isAdmin || isManager>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Формирование отчетов</a>
                </li>
            </#if>
            <#if isAdmin || isManager>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Редактирование данных</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3">
            <#if user??>
            ${name}</div>
        <#if isAdmin || isManager>
            <form action="/registration" method="get">
                <button type="submit" class="btn btn-primary mr-2">Зарегестрировать</button>
            </form>
        </#if>
        <@l.logout />

        <#else>
            <form action="/login" method="get">
                <button type="submit" class="btn btn-primary">Войти</button>
            </form>
        </#if>
    </div>
</nav>