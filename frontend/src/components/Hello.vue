<template>
    <div>
        <v-toolbar color="white">
            <v-toolbar-title>{{ $t('title') }}</v-toolbar-title>

            <div class="flex-grow-1"></div>

            <div class="">
                <v-btn text to="/login">{{ $t('login') }}</v-btn>
                <span class="pa-1"></span>
                <v-btn outlined text to="/registration">{{ $t('register') }}</v-btn>
            </div>

            <v-toolbar-items>

            </v-toolbar-items>

            <template v-if="$vuetify.breakpoint.smAndUp">
                <v-col sm="1">
                    <v-select class="mt-6" :items="locales" v-model="currentLocale" @input="changeLocale" rounded>
                    </v-select>
                </v-col>
            </template>

        </v-toolbar>
        <v-content>
            <section>
                <v-parallax src="./assets/hero.jpeg" height="600">
                    <v-layout column align-center justify-center class="white--text">
                        <img src="./assets/vuetify.png" alt="Vuetify.js" height="200">
                        <h1 class="white--text mb-2 di      1 text-center">{{ $t('projectName') }}</h1>
                        <div class="subheading mb-4 text-center">{{ $t('companyName') }}</div>
                        <v-btn class="mt-12" color="blue lighten-2" dark large href="/registration">
                            {{ $t('getStarted') }}
                        </v-btn>
                    </v-layout>
                </v-parallax>
            </section>

            <section>
                <v-layout
                        column
                        wrap
                        class="my-12"
                        align-center
                >
                    <v-flex xs12 sm4 class="my-4">
                        <div class="text-center">
                            <h2 class="headline">Cras facilisis mi vitae nunc lobortis pharetra</h2>
                            <span class="subheading">
                Nulla volutpat tincidunt ornare
              </span>
                        </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-container grid-list-xl>
                            <v-layout row wrap align-center>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">mdi-palette</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline text-center">Nullam in aliquet odio</div>
                                        </v-card-title>
                                        <v-card-text>
                                            Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat tincidunt
                                            ornare.
                                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames
                                            ac turpis egestas.
                                            Nullam in aliquet odio. Aliquam eu est vitae tellus bibendum tincidunt.
                                            Suspendisse potenti.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">mdi-flash</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline">Nulla volutpat tincidunt ornare</div>
                                        </v-card-title>
                                        <v-card-text>
                                            Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat tincidunt
                                            ornare.
                                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames
                                            ac turpis egestas.
                                            Nullam in aliquet odio. Aliquam eu est vitae tellus bibendum tincidunt.
                                            Suspendisse potenti.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">mdi-wrench</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline text-center">Suspendisse potenti</div>
                                        </v-card-title>
                                        <v-card-text>
                                            Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat tincidunt
                                            ornare.
                                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames
                                            ac turpis egestas.
                                            Nullam in aliquet odio. Aliquam eu est vitae tellus bibendum tincidunt.
                                            Suspendisse potenti.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-flex>
                </v-layout>
            </section>

            <section>
                <v-parallax src="./assets/section.jpg" height="380">
                    <v-layout column align-center justify-center>
                        <div class="headline white--text mb-4 text-center">Cras facilisis mi vitae nunc lobortis
                            pharetra
                        </div>
                        <em>Nulla volutpat tincidunt ornare</em>
                        <v-btn class="mt-12" color="blue lighten-2" dark large href="/registration">
                            {{ $t('getStarted') }}
                        </v-btn>
                    </v-layout>
                </v-parallax>
            </section>

            <section>
                <v-container grid-list-xl>
                    <v-layout row wrap justify-center class="my-12">
                        <v-flex xs12 sm4>
                            <v-card flat class="transparent">
                                <v-card-title primary-title class="layout justify-center">
                                    <div class="headline">{{ $t('companyInfo') }}</div>
                                </v-card-title>
                                <v-card-text>
                                    Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat tincidunt ornare.
                                    Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                                    egestas.
                                    Nullam in aliquet odio. Aliquam eu est vitae tellus bibendum tincidunt. Suspendisse
                                    potenti.
                                </v-card-text>
                            </v-card>
                        </v-flex>
                        <v-flex xs12 sm4 offset-sm1>
                            <v-card flat class="transparent">
                                <v-card-title primary-title class="layout justify-center">
                                    <div class="headline">{{ $t('contacts') }}</div>
                                </v-card-title>
                                <v-card-text>
                                    Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat tincidunt ornare.
                                </v-card-text>
                                <v-list class="transparent">
                                    <v-list-item>
                                        <v-list-item-action>
                                            <v-icon class="blue--text text--lighten-2">mdi-phone</v-icon>
                                        </v-list-item-action>
                                        <v-list-item-content>
                                            <v-list-item-title>555-555-555</v-list-item-title>
                                        </v-list-item-content>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-list-item-action>
                                            <v-icon class="blue--text text--lighten-2">mdi-map-marker</v-icon>
                                        </v-list-item-action>
                                        <v-list-item-content>
                                            <v-list-item-title>Lipetsk, RU</v-list-item-title>
                                        </v-list-item-content>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-list-item-action>
                                            <v-icon class="blue--text text--lighten-2">mdi-email</v-icon>
                                        </v-list-item-action>
                                        <v-list-item-content>
                                            <v-list-item-title>email@email.ru</v-list-item-title>
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-list>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-container>
            </section>

            <v-footer color="blue darken-2">
                <v-layout row wrap align-center>
                    <v-flex xs12>
                        <div class="white--text ml-4">
                            <span>&copy; {{ new Date().getFullYear() }}</span>
                        </div>
                    </v-flex>
                </v-layout>
            </v-footer>

        </v-content>
    </div>
</template>


<script>

    export default {
        data() {
            return {
                currentLocale: localStorage.getItem('locale') || navigator.language.split('-')[0],
                locales: [
                    { flag: 'us', value: 'en', text: 'English' },
                    { flag: 'ru', value: 'ru', text: 'Русский' }
                ]
            };
        },
        methods: {
            changeLocale(code) {
                this.$i18n.locale = code;
                localStorage.setItem('locale', code);
            }
        }
    }
</script>

<style scoped>

</style>
