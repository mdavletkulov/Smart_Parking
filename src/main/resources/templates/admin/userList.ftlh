<#import "../parts/common.ftlh" as c>

<@c.page>
    <script type="text/javascript" src="/static/js/tableSearch.js"></script>
    <h5> Список пользователей </h5>
    <#if message?has_content>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <div class="form-group">
        <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
    </div>
    <table class="table table-striped text-center" id="persons">
        <thead>
        <tr class="row">
            <th class="col-sm-3" scope="col">E-mail</th>
            <th class="col-sm-4" scope="col">Пользователь</th>
            <th class="col-sm-2" scope="col">Роль</th>
            <th class="col-sm-2" scope="col"></th>
            <th class="col-sm-1" scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
        <#if user.username != currentUser.username>
        <tr class="row">
            <td class="col-sm-3">${user.username}</td>
            <td class="col-sm-4">${user.getFullName()}</td>
            <td class="col-sm-2">${user.getRole()}</td>
            <td class="col-sm-2"><a href="/user/edit/${user.id}" class="btn btn-primary stretched-link mt-3">Редактировать</a>
            </td>
            <td class="col-sm-1">
                <button id="${user.getId()}" type="button" class="btn btn-primary mt-3 askDelete" data-toggle="modal">
                    Удалить
                </button>
            </td>
            </#if>
            </#list>
        </tbody>
    </table>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Подтвердите удаление</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Вы действительно хотите удалить пользователя?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var userId;
        jQuery(document).ready(function ($) {


            $('button.askDelete').click(function () {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                userId = $(this).attr('id');
                console.log(userId);
            });
        });

        $('button#delete').click(function() {
            window.location.href = '/user/delete/'+userId;
            return false;
        });
    </script>

</@c.page>