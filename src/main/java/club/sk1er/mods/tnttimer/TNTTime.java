package club.sk1er.mods.tnttimer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.text.DecimalFormat;

@Mod(modid = "tnttime", name = "TNT Time", version = "1.1.0")
public class TNTTime {

    @Mod.Instance
    public static TNTTime instance;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final DecimalFormat format = new DecimalFormat("0.00");

    @SuppressWarnings("unused")
    public void renderTag(RenderTNTPrimed tntRenderer, EntityTNTPrimed tntPrimed, double x, double y, double z, float partialTicks) {
        if (tntPrimed.fuse < 1) return;
        double distance = tntPrimed.getDistanceSqToEntity(tntRenderer.getRenderManager().livingPlayer);

        if (distance <= 4096D) {
            // todo: support bedwars obscure change in time
            float number = (tntPrimed.fuse - partialTicks) / 20F;
            String str = this.format.format(number);
            FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.0F, (float) y + tntPrimed.height + 0.5F, (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

            int xMultiplier = 1; // Nametag x rotations should flip in front-facing 3rd person

            if (mc != null && mc.gameSettings != null && mc.gameSettings.thirdPersonView == 2) {
                xMultiplier = -1;
            }

            float scale = 0.02666667f;
            GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-scale, -scale, scale);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int stringWidth = fontrenderer.getStringWidth(str) >> 1;
            float green = Math.min(tntPrimed.fuse / 80f, 1f);
            Color color = new Color(1f - green, green, 0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(-stringWidth - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(-stringWidth - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(stringWidth + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(stringWidth + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) >> 1, 0, color.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

}