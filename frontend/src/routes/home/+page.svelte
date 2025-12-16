<script>
	import { auth } from "$lib/auth";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";
	

	let payload = $state() 

	onMount(() =>{
		payload = auth.getPayload()
		
	})
</script>



<svelte:head>
    <title>Home</title>
</svelte:head>

<Content>	
    <section class="bg-white rounded-xl">
		<div class="min-h-[80vh] grid grid-cols-1 place-content-center place-items-center">
			{#if payload}
				<h1 class="font-extrabold p-2 text-center text-4xl md:text-5xl text-gray-800">Seja Bem Vindo(a), <span class="text-[#15592b]">{payload.nome}</span></h1>
				<p class="font-extralight mt-2">Utilize o menu lateral para navegar pelo sistema.</p>
				<div class="grid grid-cols-2 p-5 gap-3">
					<button class="rounded-lg p-3 bg-[#d3d013]  shadow-2xl  text-white cursor-pointer"><a href="/cadastrar/empresa">Cadastrar Nova Empresa</a></button>
					<button class="rounded-lg p-3 bg-gray-200 border-2 border-gray-300 shadow-2xl cursor-pointer"><a href="/consultar/empresa">Consultar Empresa</a></button>
				</div>
			{/if}
		</div>
	</section>
</Content>