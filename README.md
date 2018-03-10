# SDKApiInterface
基于OkHttp和Retrofit的API SDK

#### SDK说明
* 重新封装Request和Response，避免暴露内部请求内容
* 支持返回数据实体转换
* 保留同步和异步请求方式

#### 可实现的返回实体转换

增加转换类`Convert<T, R>`和可实现方法
```
    R func(T t) {
        return (R) t;
    }
```
在实际接口请求中,实现`User`实体转换成`PublicUser`
```
    public Request<PublicUser> getUser(String userName) {
        Call<User> call = mApiService.getUser(userName);
        return new Request<PublicUser>().convert(new Convert<User, PublicUser>(call) {
            @Override
            PublicUser func(User user) {
                PublicUser publicUser = new PublicUser();
                publicUser.setId(user.getId());
                publicUser.setName(user.getName());
                return publicUser;
            }
        });
    }
```
