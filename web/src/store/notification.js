import $ from 'jquery'

export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
        pulling_info: true,   // 是否正在拉取信息
        emailing: false,      // 是否正在发送邮件
        registered: false,    // 是否注册完成
        address: "",
        description: "",
        email: "",
        name: "",
        organization: "",
        identification: "",
        nickname: "",
        notification: "",
    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
            state.notification = user.notification;
        },
        updatePhoto(state, photo) {
            state.photo = photo;
        },
        updateDetail(state, detail) {
            state.address = detail.address;
            state.description = detail.description;
            state.email = detail.email;
            state.name = detail.name;
            state.organization = detail.organization;
            state.identification = detail.identification;
            state.nickname = detail.nickname;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        },
        updatePullingInfo(state, pulling_info) {
            state.pulling_info = pulling_info;
        },
        updateEmailing(state, emailing) {
            state.emailing = emailing;
        },
        updateRegistered(state, registered) {
            state.registered = registered;
        }
    },
    actions: {
        login(context, data) {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/account/token/",
                type: "post",
                data: {
                  username: data.username,
                  password: data.password,
                },
                success(resp) {
                    if(resp.error_message === "success"){
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    }
                    else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
              });
        },
        getinfo(context, data) {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        getdetail(context, data) {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/get/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateDetail", {
                            ...resp
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        logout(context) {
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        }
    },
    modules: {
    }
}
