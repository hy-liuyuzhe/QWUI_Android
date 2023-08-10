<template>
    <div class="lady-right-container">
        <div class="lady-right-banner-title">
            <span class="right-title">{{ title }}</span>
        </div>
        <div class="right-content-container">
            <slot name="content"></slot>
        </div>
    </div>
</template>
<script>
export default {
    name: "LadyRight",
    props: {
        title: String
    },
}
</script>
<style lang="scss" scoped>
.lady-right-container {
    width: 702px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.lady-right-banner-title {
    width: 480px;
    height: 72px;
    background-image: url("../assets/lady_small_banner_bg.png");
    background-size: 100% 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    z-index: 2;
    .right-title {
        height: 43px;
        text-align: center;
        font-size: 36px;
        font-family: SFUIText-BoldItalic, SFUIText;
        font-weight: bold;
        color: #FFFFFF;
        line-height: 43px;
        margin: auto;
    }
}
.right-content-container {
    width: 702px;
    background: #FFFAF2;
    border-radius: 40px;
    border: 7px solid;
    border-color: #F8DB8F;
    margin-top: -32px;
    z-index: 1;
}
</style>