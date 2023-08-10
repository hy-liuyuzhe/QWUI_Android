<template>
  <div class="exchange-confirm-v-layout">
    <span class="message-text-view">
      {{ $t("super_gift_lottery_sure_exchange") }}
    </span>
    <div class="btns-h-layout">
      <span class="cancel-button" @click="$emit('on-cancel-btn-click')">
        {{ $t("common_cancel") }}
      </span>
      <span class="confirm-button" @click="$emit('on-confirm-btn-click')">
        {{ $t("common_confirm") }}
      </span>
    </div>
  </div>
</template>
<script>
export default {
  name: "ExchangeConfirmDialog",
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(exchange-confirm) {
  width: 606px;
  height: 272px;
  padding: 48px;
  background: linear-gradient(0deg, #e8dfb4, #dec47c);
  border-radius: 32px;
  @include text-view(
    message,
    $font-size: 32px,
    $line-height: 48px,
    $font-color: $--color-953E00,
    $font-weight: $--font-weight-regular
  );

  @include h-layout(btns) {
    width: 100%;
    justify-content: space-between;
    margin-top: 40px;
    @include button(
      cancel,
      $width: 239px,
      $height: 88px,
      $text-size: 32px,
      $text-color: $--color-white,
      $border-radius: 44px,
      $background: $--color-E5BB67
    );

    @include button(
      confirm,
      $width: 239px,
      $height: 88px,
      $text-size: 32px,
      $text-color: $--color-white,
      $border-radius: 44px,
      $background: linear-gradient(90deg, #901ea9, #de50ea)
    );
  }
}
</style>