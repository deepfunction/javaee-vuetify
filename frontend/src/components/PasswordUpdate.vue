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

        <v-container fill-height fluid>
            <v-row align="center" justify="center">
                <v-col cols="12" sm="8" md="4">
                    <v-card class="elevation-12">

                        <v-card-text>
                            <v-form>

                                <v-text-field
                                        ref="password"
                                        :label="$t('password')"
                                        name="password"
                                        type="password"
                                        outlined
                                        :error-messages="passwordErrors"
                                        @input="$v.password.$touch()"
                                        @blur="$v.password.$touch()"
                                        required
                                        counter
                                        :append-icon="'visibility_off'"
                                        v-model="password"
                                ></v-text-field>

                                <v-text-field
                                        ref="confirmPassword"
                                        :label="$t('confirmPassword')"
                                        name="confirmPassword"
                                        type="password"
                                        outlined
                                        :error-messages="confirmPasswordErrors"
                                        @input="$v.confirmPassword.$touch()"
                                        @blur="$v.confirmPassword.$touch()"
                                        required
                                        :append-icon="'visibility_off'"
                                        v-model="confirmPassword"
                                ></v-text-field>

                                <v-btn color="success" style="min-height: 50px" class="v-btn--block"
                                       @click="updatePassword()" :disabled='inProgress, inLoading'>
                                    {{ $t('updatePassword') }}
                                </v-btn>

                            </v-form>
                        </v-card-text>
                    </v-card>

                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>

    import {required, helpers, sameAs} from 'vuelidate/lib/validators'

    const passwordRegex = helpers.regex('password', /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/);

    export default {
        name: "PasswordUpdate",
        data() {
            return {
                password: '',
                confirmPassword: '',
                message: '',
                snackbarError: false,
                inProgress: false,
                inLoading: false,
                code: this.$route.params.code,
            }
        },
        validations: {
            password: {
                required,
                passwordRegex
            },
            confirmPassword: {
                required,
                sameAsPassword: sameAs('password')
            }
        },
        computed: {
            passwordErrors() {
                const errors = [];
                if (!this.$v.password.$dirty) return errors;
                !this.$v.password.required && errors.push(this.$i18n.t('passwordRequired'));
                !this.$v.password.passwordRegex && errors.push(this.$i18n.t('passwordFormat'));
                return errors;
            },
            confirmPasswordErrors() {
                const errors = [];
                if (!this.$v.confirmPassword.$dirty) return errors;
                !this.$v.confirmPassword.required && errors.push(this.$i18n.t('passwordRequired'));
                !this.$v.confirmPassword.sameAsPassword && errors.push(this.$i18n.t('passwordMatch'));
                return errors;
            },
        },
        methods: {
            updatePassword() {
                this.inProgress = true;
                this.inLoading = true;
                if (this.$v.$invalid) {
                    this.inProgress = false;
                    this.inLoading = false;
                } else {
                    this.$store.dispatch("updatePassword", {code: this.code, password: this.confirmPassword})
                        .then(response => {
                            if (response.data.success) {
                                this.$router.push('/login');
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
        }
    }
</script>

<style scoped>

</style>