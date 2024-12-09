import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tailwindcss from "tailwindcss";

// https://vite.dev/config/
export default defineConfig({
  server: {
    proxy: {
      'api': {
        target: 'http://localhost:18080',
        changeOrigin: true
      }
    }
  },
  plugins: [react()],
  css: {
    postcss: {
      plugins: [tailwindcss()]
    }
  }
})
