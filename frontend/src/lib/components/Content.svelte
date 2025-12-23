<script>
	import Header from "./Header.svelte";
	import Sidebar from "./Sidebar.svelte";

	export let title = "SGI";
	
</script>




<div class="min-h-screen grid grid-rows-[auto_1fr] grid-cols-[18rem_1fr] bg-[#94AECA]">
	<!-- HEADER: linha 1, ocupa as 2 colunas -->
	<header

   class="row-end-1 col-span-2 md:row-start-1 md:row-end-1 md:col-span-2 bg-[#0F2037] flex items-center justify-center shadow-2xl py-3 relative text-3xl  "
>	<Header {title} />
	</header>

	<!-- SIDEBAR: linha 2, coluna 1 -->
	<aside class=" bg-gradient-to-b to-[#3C4653] from-[#272E36]  text-white shadow-2xl rounded-br-full border-b-9 border-b-white border-r border-r-white relative md:block md:row-start-1 md:row-end-3 md:col-start-1 md:col-end-2
		md:translate-x-0 hidden">
		<Sidebar />
	</aside>

	<!-- MAIN: linha 2, coluna 2 -->
	<main class="col-span-2 md:row-start-2 md:row-end-3 md:col-start-2 md:col-end-4  row-start-1 bg-gray-200 p-4">
		<slot />
	</main>
</div>


