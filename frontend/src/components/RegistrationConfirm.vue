<template>

</template>

<script>

    export default {
        name: "RegistrationConfirm",
        data() {
            return {
                code: this.$route.params.code
            }
        },
        created() {
            this.$store.dispatch("registrationConfirm", {code: this.code})
                .then(response => {
                    if (response.data.success) {
                        this.$router.push('/login');
                    } else {
                        this.$router.push('/registration');
                    }
                })
                .catch(() => {
                    this.$store.dispatch("addMessage", {message: this.$i18n.t('connectionLost')});
                    this.$router.push('/registration');
                })
        }
    }
</script>

<style scoped>

</style>