package ru.pschsch.pschschapps.githubwork.RetrofitUtils;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/** ******************************Retrofit 2************************************************
 * - @Path - аннотация указывает, какое значение будет вставлено на место фигурных скобок
 * в параметрах аннотации запроса(аналог интерполяции в С#)
 * - Call<T> - собственно тип ответа от сервера, который мы ожидаем, если нам не важен тип,
 * можем просто указать Call<Response>.
 * - Для простейшей авторизации используется кодировка Base64. Для простейшей авторизации
 * используется специальный header, который указывается непосредственно на сервере. Т.е, для того
 * чтобы пройти авторизацию на сервере, в запрос необходимо добавить этот заголовок.
 * HTTP-запросы: когда мы получаем данные с сервера, мы делает GET-запрос.
 * Для отправки данных, мы используем, как правило, POST или PUT запрос.
 * POST-запрос необходим для отправки новых данных на сервер, PUT - для добавления уже существующих.
 * POST мы используем для создания, например, нового аккаунта, а PUT - для добавления,
 * например, новой информации о пользователе.
 * *******************************************************************************************
 * Логирование запросов: логирование запросов очень полезно, когда мы хотим посмотреть, что
 * именно отправляет и получает Retrofit при работе с сервером. Это важно, когда мы хотим избежать
 * ошибок при работе с сервером. Для логирования запросов используется дополнение к библиотеке
 * OkHttp - LoggingInterceptor. Это специальный интерсептор, логирующий проходящую информацию.
 * Имеется 4 уровня логирования, которые задаются с помощью метода setLevel(). Уровни
 * представлены в виде констант перечисления, встроенного в класс LoggingInterceptor-а.
 * 1)NONE - без логирования, 2) BASIC - логирует реквесты и респонзы и ничего больше.
 * 3)HEADERS - логирует реквесты и респонзы, а также заголовки. 4)BODY - логирует все.
 * Retrofit в базовой конфигурации использует базовый объект
 * OkHttp, для кастомизации клиента используется метод client(OkHttpClient client) - принимающий
 * объект OkHttpClient, в котором мы можем кастомизировать свойства клиента.
 * Пример лога уровня BASIC для GET-запроса на GitHub:
 * 2019-01-02 16:45:02.779 1487-1549/ru.pschsch.pschschapps.githubwork D/OkHttp: --> GET https://api.github.com/users/Pschsch/repos
 * 2019-01-02 16:45:03.713 1487-1549/ru.pschsch.pschschapps.githubwork D/OkHttp: <-- 200 OK https://api.github.com/users/Pschsch/repos (933ms, unknown-length body)
 * ВАЖНО! Данный интерсептор логирует АБСОЛЮТНО ВСЕ в режиме BODY! Включая конфидециальные данные
 * такие, как, например, пароли и access-токены. Поэтому логирование запросов должно отсутствовать
 * в релиз-билде приложения. Можно поставить логирование в проверку на флаг дебаг-билда, тогда
 * в релиз-билде логирование выполняться не будет.
 * Стоит также помнить, что при отправке каких-либо больших файлов по сети, логгер также будет
 * логировать весь этот контент в виде потока байтов, что неибежно приведет к засорению лога)
 * ***********************************************************************************************************************************************************************
 * Отправка файлов на сервер:
 * Мультизапросы - запросы, использующиеся для передачи бинарных файлов на сервер, таких как
 * фото, аудио, фидео и т.д. Этот запрос разделен на множество различных частей, но при этом остается
 * единым запросом. Retrofit поддерживает мультизапросы из коробки, для мультизапросов используются
 * специальные аннотации. Для отправки файлов не забыть добавить пермишны на чтение и запись
 * данных с устройства. Для отправки файлов используются POST- или PUT-запросы.
 * В этом проекте рассмотрен вариант отправки изображения. при наличии API, наш код будет
 * отправлять изображение))
 * Аннотация @Multipart помечает запрос как множественный.
 * Retrofit использует класс MultipartBody из библиотеки OkHttp для отправки бинарных файлов
 * соответсвенно нам необходимо пометить изображение как экземпляр этого класса. Приложим
 * описание: описание - обычная строка, нет нужды отправлять ее множественным запросом
 * Так как описание и фото - части запроса, нужно отметить их как часть запроса аннотацией Part
 * Для частей простого RequestBody необходимо указать имя этой части запроса, для MultipartBody
 * это не требуется
 * Если у нас простой RequestBody, то мы можем использовать тип String, Retrofit сам изменит
 * его на RequestBody, а затем на MultipartBody.Part. Части запроса могут быть null.
 * Используем возвращаемый тип ResponseBody - т.е нам не важно, какой тип будет возвращен.
 * при использовании этого типа, над пришедшими от сервера данными не будет проведено никаких
 * манипуляций, например, конвертации, или получение Java-объектов. Все что мы можем сделать -
 * это получить код ответа от сервера или тело ответа в виде строки.
 * Чтобы отправлять файлы, нам необходимо настроить запрос перед отправкой.
 * Множественный запрос идет в виде формы, если к файлу мы доюавляем описание. Этот параметр
 * нужно настроить в объекте RequestBody
 * ********************************************************************************************
 * OAuth-авторизация - наиболее часто встречающийся вид авторизации. После прохождения процедуры
 * авторизации , сервис предоставляет доступ к http-методам своего API. Необходимо
 * зарегистрировать приложение-клиент непосредственно у сервиса, ск оторым будет происходить
 * работа. После прохождения этой процедуры, сервис выдает клиент-ид и клиент-секрет.
 * После авторизации пользователя в приложении, сервис возвращает access-токен.
 * @Query - аннотация позволяет добавлять параметры запроса, эти параметры будут добавляться
 * в конец URL.
 * */
public interface RetrofitInfo {
    //Endpoint POST-запроса на отправку изображения
    @Multipart
    @POST("api/example/upload")
    Call<ResponseBody> uploadPhoto(
            @Part("photoDescription") String description,
            @Part MultipartBody.Part photo //часть множественного запроса
    );
}
