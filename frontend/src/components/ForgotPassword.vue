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

        <v-container fill-height fluid>
            <v-row align="center" justify="center">
                <v-col cols="12" sm="8" md="4">
                    <v-card class="elevation-12">

                        <v-card-text>
                            <v-form>

                                <v-text-field
                                        ref="email"
                                        :label="$t('enterYourEmail')"
                                        name="email"
                                        type="text"
                                        outlined
                                        :error-messages="emailErrors"
                                        @input="$v.email.$touch()"
                                        @blur="$v.email.$touch()"
                                        required
                                        v-model="email"
                                        :disabled='isSuccess'
                                ></v-text-field>

                                <v-btn color="success" style="min-height: 50px" class="v-btn--block" @click="sendPasswordUpdateEmail()" :disabled='inProgress, inLoading'>
                                    {{ $t('sendPasswordUpdateEmail') }}
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

    import { required } from 'vuelidate/lib/validators'

    export default {
        name: "ForgotPassword",
        data() {
            return {
                email: '',
                message: '',
                snackbarError: false,
                snackbarSuccess: false,
                inProgress: false,
                inLoading: false,
                isSuccess: false,
            }
        },
        validations: {
            email: {
                required
            }
        },
        computed: {
            emailErrors() {
                const errors = [];
                if (!this.$v.email.$dirty) return errors;
                !this.$v.email.required && errors.push(this.$i18n.t('emailRequired'));
                return errors;
            }
        },
        methods: {
            sendPasswordUpdateEmail(){
                this.inProgress = true;
                this.inLoading = true;
                    if (this.$v.$invalid) {
                        this.inProgress = false;
                        this.inLoading = false;
                    } else {
                        this.$store.dispatch("sendPasswordUpdateEmail", {email: this.email})
                            .then(response => {
                                if (response.data.success) {
                                    this.isSuccess = true;
                                    this.message = response.data.message;
                                    this.snackbarSuccess = true;
                                    this.inLoading = false;
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