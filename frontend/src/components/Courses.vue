<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>

        <v-snackbar color="error" top v-model="snackbarError" :timeout="15000">
            <span>{{ message }}</span>
            <v-btn text @click="snackbarError = false">{{ $t('close') }}</v-btn>
        </v-snackbar>

        <v-toolbar color="white">
            <v-toolbar-title>{{ $t('title') }}</v-toolbar-title>
            <div class="flex-grow-1"></div>
            <div>
                <v-row justify="center">
                    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">

                        <template v-slot:activator="{ on }">
                            <div>
                                <v-btn text v-on="on">
                                    <v-badge color="cyan" left>
                                        <template v-slot:badge>
                                            <span :class="[getNumberOfItemsAddedToCart > 0 ? 'tag is-info' : '']">{{ getNumberOfItemsAddedToCart }}</span>
                                        </template>
                                        <v-icon large color="grey lighten-1">shopping_cart</v-icon>
                                    </v-badge>
                                </v-btn>
                                <span class="pa-1"></span>
                                <v-btn outlined text @click="logout()">
                                    {{ $t('logout') }}
                                </v-btn>
                            </div>
                        </template>

                        <v-card>
                            <v-toolbar dark color="primary">
                                <v-btn icon dark @click="dialog = false">
                                    <v-icon>mdi-close</v-icon>
                                </v-btn>
                                <v-toolbar-title>Checkout</v-toolbar-title>
                                <div class="flex-grow-1"></div>
                            </v-toolbar>

                            <v-container>
                                <v-list v-for="item in getItemsInCart" :key="item.name">
                                    <v-list-item three-line>
                                        <v-list-item-content>
                                            <v-list-item-title class="headline font-weight-bold">{{ item.name }}
                                            </v-list-item-title>
                                            <v-list-item-subtitle class="subtitle-1">{{ item.subtotal }} $
                                            </v-list-item-subtitle>
                                        </v-list-item-content>
                                        <v-btn v-if="item.isAddedToCart" text color="error" @click="removeFromCart(item)">
                                            {{ $t('removeFromCart') }}
                                        </v-btn>
                                    </v-list-item>
                                    <v-divider></v-divider>
                                </v-list>

                                <span v-if="getItemsInCart.length === 0">{{ $t('cartEmpty') }}</span>

                                <v-list v-show="getItemsInCart.length > 0">
                                    <v-list-item>
                                        <v-list-item-content>
                                            <v-list-item-title class="headline font-weight-bold">
                                                {{ $t('completeCheckout') }}
                                            </v-list-item-title>
                                            <v-list-item-subtitle class="headline">
                                                {{ getTotalPrice }} $
                                            </v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-list>

                                <div>
                                    <v-btn v-show="getItemsInCart.length > 0" color="success" @click="completeCheckout">
                                        {{ $t('completeCheckout') }}
                                    </v-btn>
                                    <v-overlay :value="overlay">
                                        <v-progress-circular indeterminate size="64"></v-progress-circular>
                                    </v-overlay>
                                </div>

                            </v-container>
                        </v-card>
                    </v-dialog>
                </v-row>
            </div>
            <v-toolbar-items>
            </v-toolbar-items>
            <template v-if="$vuetify.breakpoint.smAndUp"></template>
        </v-toolbar>
        <v-container fluid>
            <v-data-iterator :items="items" :items-per-page.sync="itemsPerPage" :page="page" :search="search"
                             :sort-by="sortBy.toLowerCase()" :sort-desc="sortDesc" hide-default-footer>
                <template v-slot:header>
                    <v-toolbar dark color="blue darken-3" class="mb-1">
                        <v-text-field v-model="search" clearable flat solo-inverted hide-details
                                      prepend-inner-icon="search" label="Search"></v-text-field>
                        <template v-if="$vuetify.breakpoint.mdAndUp">
                            <div class="flex-grow-1"></div>
                            <v-select v-model="sortBy" flat solo-inverted hide-details :items="keys"
                                      prepend-inner-icon="search" label="Sort by"></v-select>
                            <div class="flex-grow-1"></div>

                            <v-btn-toggle v-model="sortDesc" mandatory>
                                <v-btn large depressed color="blue" :value="false">
                                    <v-icon>mdi-arrow-up</v-icon>
                                </v-btn>

                                <v-btn large depressed color="blue" :value="true">
                                    <v-icon>mdi-arrow-down</v-icon>
                                </v-btn>
                            </v-btn-toggle>

                        </template>
                    </v-toolbar>
                </template>

                <template v-slot:default="props">
                    <v-row>
                        <v-col v-for="item in props.items" :key="item.name" cols="12" sm="6" md="4" lg="3">
                            <v-card :disabled='item.isBusy || !item.isAvailable'>
                                <v-card-title class="subheading font-weight-bold">{{ item.name }}</v-card-title>
                                <v-divider></v-divider>

                                <v-list dense v-show="!item.isBought && !item.isBusy && item.isAvailable" >
                                    <v-list-item v-for="(key, index) in getFilteredKeys" :key="index"
                                                 :color="sortBy === key ? `blue lighten-4` : `white`">
                                        <v-list-item-content>{{ key }}:</v-list-item-content>
                                        <v-list-item-content class="align-end">{{ item[key.toLowerCase()] }} $
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-list>

                                <v-card-actions>

                                    <v-btn v-if="!item.isAddedToCart && !item.isBought && item.isAvailable" text color="success"
                                           @click="addToCart(item)">
                                        {{ $t('addToCart') }}
                                    </v-btn>

                                    <v-btn v-if="item.isAddedToCart && !item.isBought" text color="error"
                                           @click="removeFromCart(item)">
                                        {{ $t('removeFromCart') }}
                                    </v-btn>

                                    <v-btn v-if="item.isBought && !item.isBusy" text color="success">
                                        {{ $t('goToCourse') }}
                                    </v-btn>

                                    <v-btn v-if="item.isBusy || !item.isAvailable" text color="error">
                                        {{ $t('courseUnavailable') }}
                                    </v-btn>

                                    <div class="flex-grow-1"></div>
                                </v-card-actions>

                            </v-card>
                        </v-col>
                    </v-row>
                </template>

                <template v-slot:footer>
                    <v-row class="mt-2" align="center" justify="center">
                        <span class="grey--text">Items per page</span>
                        <v-menu offset-y>

                            <template v-slot:activator="{ on }">
                                <v-btn dark text color="primary" class="ml-2" v-on="on">
                                    {{ itemsPerPage }}
                                    <v-icon>mdi-chevron-down</v-icon>
                                </v-btn>
                            </template>

                            <v-list>
                                <v-list-item v-for="(number, index) in itemsPerPageArray" :key="index"
                                             @click="updateItemsPerPage(number)">
                                    <v-list-item-title>{{ number }}</v-list-item-title>
                                </v-list-item>
                            </v-list>

                        </v-menu>
                        <div class="flex-grow-1"></div>
                        <span class="mr-4 grey--text">Page {{ page }} of {{ getNumberOfPages }}</span>

                        <v-btn fab dark color="blue darken-3" class="mr-1" @click="goToFormerPage">
                            <v-icon>mdi-chevron-left</v-icon>
                        </v-btn>

                        <v-btn fab dark color="blue darken-3" class="ml-1" @click="goToNextPage">
                            <v-icon>mdi-chevron-right</v-icon>
                        </v-btn>

                    </v-row>
                </template>
            </v-data-iterator>

            <v-footer fixed color="blue darken-2">
                <v-layout row wrap align-center>
                    <v-flex xs12>
                        <div class="white--text ml-4">
                            <span>&copy; {{ new Date().getFullYear() }}</span>
                        </div>
                    </v-flex>
                </v-layout>
            </v-footer>

        </v-container>

    </div>
</template>

<script>

    import Vue from 'vue'
    import api from './backend-api'
    import i18n from '../plugins/i18n'

    export default {
        data() {
            return {
                itemsPerPageArray: [4, 8],
                search: '',
                filter: {},
                sortDesc: false,
                page: 1,
                itemsPerPage: 4,
                sortBy: 'name',
                show: false,
                keys: [
                ],
                items: [
                ],
                overlay: false,
                dialog: false,
                notifications: false,
                sound: true,
                widgets: false,
                snackbarError: false,
                message: '',
                userEmail: this.$session.get('userEmail'),
                deviceId: this.$cookie.get('deviceId'),
                token: this.$cookie.get('token')
            }
        },
        computed: {
            getNumberOfPages() {
                return Math.ceil(this.$store.getters.getItems.length / this.itemsPerPage)
            },
            getFilteredKeys() {
                return this.$store.getters.getKeys.filter(key => key !== `Name`)
            },
            getNumberOfItemsAddedToCart() {
                return this.$store.getters.getItemsInCart.length;
            },
            getItemsInCart() {
                return this.$store.getters.getItemsInCart;
            },
            getTotalPrice() {
                let itemsAdded = this.$store.getters.getItemsInCart,
                    pricesArray = [],
                    finalPrice = '';
                itemsAdded.forEach(item => {
                    pricesArray.push((item.subtotal));
                });
                finalPrice = pricesArray.reduce((a, b) => a + b, 0);
                return finalPrice;
            },
            getIsAvailable() {
              return this.$store.getters.getIsAvailable;
            },
        },
        created() {
            this.getItems();
        },
        methods: {
            goToNextPage() {
                if (this.page + 1 <= this.getNumberOfPages) this.page += 1
            },
            goToFormerPage() {
                if (this.page - 1 >= 1) this.page -= 1
            },
            updateItemsPerPage(number) {
                this.itemsPerPage = number
            },
            addToCart(item) {
                this.$store.dispatch('addItemToCart', {
                    item: item
                });
            },
            removeFromCart(item) {
                this.$store.dispatch('removeItemFromCart', {
                    item: item
                });
            },
            getItems() {
                this.$store.dispatch("getItems", {token: this.token}).then(response => {
                    if (response.data.success) {
                        this.checkUserIsBoughtItem();
                        this.checkIsBusyItem();
                        this.calculateDiscount();
                        this.keys = this.$store.getters.getKeys;
                        this.items = this.$store.getters.getItems;
                    } else {
                        this.message = response.data.message;
                        this.snackbarError = true;
                    }
                }).catch(error => {
                        console.log(error);
                        this.message = i18n.t('connectionLost');
                        this.snackbarError = true;
                    })
            },
            calculateDiscount() {
                this.$store.dispatch('calculateDiscount', {boughtItemsCount: this.$session.get('boughtItemsCount')});
            },
            checkUserIsBoughtItem() {
                this.$store.dispatch('checkUserIsBoughtItem', {
                    userEmail: this.userEmail
                });
            },
            checkIsBusyItem() {
                this.$store.dispatch('checkIsBusyItem', {
                    userEmail: this.userEmail
                });
            },
            completeCheckout() {
                this.overlay = true;
                this.$store.dispatch("completeCheckout", {userEmail: this.userEmail, token: this.token}).then(response => {
                    if (!response.data.success) {
                        this.message = response.data.message;
                        this.snackbarError = true;
                    }
                    location.reload();
                }).catch(error => {
                    this.overlay = false;
                    console.log(error);
                    this.message = i18n.t('connectionLost');
                    this.snackbarError = true;
                })
            },
            logout() {
                this.$session.destroy();
                this.$cookie.delete('token');
                if (this.deviceId != null) api.deleteAccessTokenByDeviceId(this.deviceId);
                this.$router.push('/');
            }
        },
    }
</script>
