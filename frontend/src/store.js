import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        message: '',
        keys: [
            'Name',
            'Price',
            'Discount',
            'Subtotal'
        ],
        items: [],
        cart: [],
        userItems: []
    },
    mutations: {
        GET_ITEMS: (state, items) => {
            state.items = items;
        },
        ADD_ITEM_TO_CART: (state, object) => {
            state.cart.push(object.item);
            state.items.forEach(it => {
                if (object.item.name === it.name) {
                    it.isAddedToCart = true;
                }
                if (object.item.name === "Java") {
                    if (it.name === "C#") {
                        it.isAvailable = false;
                    }
                }
                if (object.item.name === "C#") {
                    if (it.name === "Java") {
                        it.isAvailable = false;
                    }
                }
            });
        },
        ADD_USER_ITEMS: (state, object) => {
            state.items.forEach(it => {
                if (it.isBought && it.userEmail === object.userEmail) {
                    state.userItems.push(it.name);
                }
            });
        },
        REMOVE_ITEM_FROM_CART: (state, object) => {
            state.cart.forEach(function (it, index, item) {
                if (object.item.name === it.name) {
                    item.splice(index, 1);
                }
            });
            state.items.forEach(it => {
                if (object.item.name === it.name) {
                    it.isAddedToCart = false;
                }
                if (object.item.name === "Java") {
                    if (it.name === "C#") {
                        it.isAvailable = true;
                    }
                }
                if (object.item.name === "C#") {
                    if (it.name === "Java") {
                        it.isAvailable = true;
                    }
                }
            });
        },
        CALCULATE_DISCOUNT: (state, object) => {
            let boughtItemsCount = object.boughtItemsCount;
            state.items.forEach(it => {
                it.discount = it.price * (boughtItemsCount * 10) / 100;
                it.subtotal = it.price - it.discount;
            });
        },
        CHECK_USER_IS_BOUGHT_ITEM: (state, object) => {
            state.items.forEach(it => {
                if (it.userEmail === object.userEmail) {
                    it.isBought = true;
                }
                if (it.userEmail === object.userEmail && it.isBought && it.name === 'C#') {
                    state.items.forEach(it => {
                        if (it.name === 'Java') {
                            it.isAvailable = false;
                        }
                    })
                }
                if (it.userEmail === object.userEmail && it.isBought && it.name === 'Java') {
                    state.items.forEach(it => {
                        if (it.name === 'C#') {
                            it.isAvailable = false;
                        }
                    })
                }
            });
        },
        CHECK_IS_BUSY_ITEM: (state, object) => {
            state.items.forEach(it => {
                if (!(it.userEmail === object.userEmail) && !(it.userEmail === '')) {
                    it.isBusy = true;
                }
            });
        },
        ADD_MESSAGE: (state, object) => {
            state.message = object.message;
        }
    },
    actions: {
        login(context, object) {
            return new Promise((resolve, reject) => {
                api.login(object.email, object.password, object.deviceId)
                    .then(response => {
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },
        register(context, object) {
            return new Promise((resolve, reject) => {
                api.register(object.email, object.password)
                    .then(response => {
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },
        registrationConfirm(context, object) {
            return new Promise((resolve, reject) => {
                api.registrationConfirm(object.code)
                    .then(response => {
                        context.commit('ADD_MESSAGE', {message: response.data.message});
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },
        sendPasswordUpdateEmail(context, object) {
            return new Promise((resolve, reject) => {
                api.sendPasswordUpdateEmail(object.email)
                    .then(response => {
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },
        updatePassword(context, object) {
            return new Promise((resolve, reject) => {
                api.updatePassword(object.code, object.password)
                    .then(response => {
                        context.commit('ADD_MESSAGE', {message: response.data.message});
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },
        getItems(context, object) {
            return new Promise((resolve, reject) => {
                api.getItems(object.token)
                    .then(response => {
                        if (response.data.success) {
                            context.commit('GET_ITEMS', response.data.courses)
                        }
                        resolve(response)
                    }).catch(error => {
                    reject(error);
                })
            })
        },
        addUserItems: (context, object) => {
            context.commit('ADD_USER_ITEMS', object);
        },
        addItemToCart: (context, object) => {
            context.commit('ADD_ITEM_TO_CART', object);
        },
        removeItemFromCart: (context, object) => {
            context.commit('REMOVE_ITEM_FROM_CART', object);
        },
        calculateDiscount: (context, object) => {
            context.commit('CALCULATE_DISCOUNT', object);
        },
        checkUserIsBoughtItem: (context, object) => {
            context.commit('CHECK_USER_IS_BOUGHT_ITEM', object);
        },
        checkIsBusyItem: (context, object) => {
            context.commit('CHECK_IS_BUSY_ITEM', object);
        },
        completeCheckout: (context, object) => {
            return new Promise((resolve, reject) => {
                let items = context.state.cart;
                items.forEach(it => {
                    it.isAddedToCart = false;
                    it.isBought = true;
                    it.isAvailable = true;
                    it.userEmail = object.userEmail;
                });
                api.completeCheckout(items, object.token)
                    .then(response => {
                        resolve(response)
                    }).catch(error => {
                    reject(error);
                })
            })
        },
        addMessage: (context, object) => {
            context.commit('ADD_MESSAGE', object);
        }
    },
    getters: {
        getKeys: state => {
            return state.keys;
        },
        getItems: state => {
            return state.items;
        },
        getUserItems: state => {
            return state.userItems;
        },
        getMessage: state => {
            return state.message;
        },
        getItemsInCart: state => {
            return state.items.filter(it => {
                return it.isAddedToCart;
            });
        },
        getIsAvailable: state => {
            return state.items.filter(it => {
                return it.isAvailable;
            });
        }
    }
});