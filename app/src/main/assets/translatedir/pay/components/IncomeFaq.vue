<template>
    <div class="faq-page">
        <p class="content-title">{{ $t("what_is_diamond") }}</p>
        <p class="content-desc">{{ $t("what_is_diamond_desc") }}</p>
        <p class="content-desc">{{ $t("diamond_desc_1") }}</p>
        <!-- <p class="content-desc">{{ $t("diamond_desc_2") }}</p> -->
    </div>
</template>
<script>
export default {
  name: "IncomeFaq",
};
</script>
<style scoped>
.faq-page {
    padding-left: 40px;
    padding-right: 40px;
    padding-top: 40px;
    padding-bottom: 200px;
}
.content-title {
    font-size: 32px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #222222;
    margin: 0px;
}
.content-desc {
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #777777;
    line-height: 34px;
}
</style>