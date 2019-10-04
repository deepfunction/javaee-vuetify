<template>
    <div class="fill-height">

        <v-progress-linear
                :active="inLoading"
                :indeterminate="inLoading"
                absolute
                color="deep-purple accent-4"
        ></v-progress-linear>

        <v-snackbar color="error" top v-model="snackbarError" :timeout="15000">
            <span>{{ message }}</span>
            <v-btn text @click="snackbarError = false">{{ $t('close') }}</v-btn>
        </v-snackbar>

        <v-snackbar color="success" top v-model="snackbarSuccess" :timeout="15000">
            <span>{{ message }}</span>
            <v-btn text @click="snackbarSuccess = false">{{ $t('close') }}</v-btn>
        </v-snackbar>

        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md4>
                    <v-card class="elevation-12">

                        <v-card-text>
                            <v-form>
                                <v-text-field
                                        ref="email"
                                        :label="$t('email')"
                                        name="email"
                                        type="text"
                                        outlined
                                        :error-messages="emailErrors"
                                        @input="$v.email.$touch()"
                                        @blur="$v.email.$touch()"
                                        required
                                        :disabled='isSuccess'
                                        v-model="email"
                                ></v-text-field>
                                <v-text-field
                                        ref="password"
                                        :label="$t('password')"
                                        name="password"
                                        type="password"
                                        outlined
                                        counter
                                        :error-messages="passwordErrors"
                                        @input="$v.password.$touch()"
                                        @blur="$v.password.$touch()"
                                        required
                                        :append-icon="'visibility_off'"
                                        :disabled='isSuccess'
                                        v-model="password"
                                ></v-text-field>
                                <v-btn color="success" style="min-height: 50px" class="v-btn--block" @click="register()"
                                       :disabled='inProgress, inLoading'>
                                    {{ $t('register') }}
                                </v-btn>
                            </v-form>
                        </v-card-text>
                    </v-card>

                    <v-col class="text-center">
                        <div class="my-8 subtitle-1">
                            <span v-once>{{ $t('haveAccount') }} <router-link
                                    to="/login">{{ $t('login') }}</router-link></span>
                        </div>
                    </v-col>

                </v-flex>
            </v-layout>

        </v-container>
    </div>
</template>

<script>

    import { required, helpers, email } from 'vuelidate/lib/validators'

    const emailRegex = helpers.regex('email', /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/);
    const passwordRegex = helpers.regex('password', /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/);

    export default {
        name: 'Registration',
        data() {
            return {
                email: '',
                password: '',
                message: false,
                snackbarError: false,
                snackbarSuccess: false,
                inProgress: false,
                inLoading: false,
                isSuccess: false,
            }
        },
        validations: {
            email: {
                required,
                emailRegex
            },
            password: {
                required,
                passwordRegex
            }
        },
        computed: {
            emailErrors() {
                const errors = [];
                if (!this.$v.email.$dirty) return errors;
                !this.$v.email.required && errors.push(this.$i18n.t('emailRequired'));
                !this.$v.email.emailRegex && errors.push(this.$i18n.t('emailNotValid'));
                return errors;
            },
            passwordErrors() {
                const errors = [];
                if (!this.$v.password.$dirty) return errors;
                !this.$v.password.required && errors.push(this.$i18n.t('passwordRequired'));
                !this.$v.password.passwordRegex && errors.push(this.$i18n.t('passwordFormat'));
                return errors;
            },
        },
        methods: {
            register() {
                this.inProgress = true;
                this.inLoading = true;
                if (this.$v.$invalid) {
                    this.inProgress = false;
                    this.inLoading = false;
                } else {
                    this.$store.dispatch("register", {email: this.email, password: this.password})
                        .then(response => {
                            if (response.data.success) {
                                this.isSuccess = true;
                                this.message = response.data.message;
                                this.inLoading = false;
                                this.snackbarSuccess = true;
                            } else {
                                this.inProgress = false;
                                this.inLoading = false;
                                this.message = response.data.message;
                                this.snackbarError = true;
                            }
                        })
                        .catch(error => {
                            console.log(error);
                            this.message = this.$i18n.t('connectionLost');
                            this.snackbarError = true;
                            this.inProgress = false;
                            this.inLoading = false;
                        })

                }
            }
        },
        created() {
            if (this.$store.getters.getMessage !== '') {
                this.message = this.$store.getters.getMessage;
                this.snackbarError = true;
                this.$store.dispatch("addMessage", {message: ''});
            }
        }
    }
</script>

<style scoped>

</style>
