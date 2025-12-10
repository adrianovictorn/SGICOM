<script>
	import Menu from "./Menu.svelte";
	import UserComponent from "./UserComponent.svelte";

	export let title = "";
	export let aberto = false;
</script>

<div class="w-full py-3 px-4 grid items-center grid-cols-[1fr_auto_1fr] ">

	<div></div>

	
	<div class="flex  justify-center ">
		<h1 class="text-[#0F2037] bg-gradient-to-r  from-[#0F2037] to-[#fef603] font-bold text-center "><span class="text-white font-extralight bg-[#0F2037]">{title} </span>COM</h1>
		
	</div>

	<div class="flex justify-end items-center gap-2 ">
		<button type="button" class="md:hidden" onclick={() => (aberto = !aberto)} aria-label="Abrir menu">
			<svg class="w-6 h-6 text-white dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
			<path stroke="currentColor" stroke-linecap="round" stroke-width="2" d="M5 7h14M5 12h14M5 17h14"/>
			</svg>
		</button>
		<div class="hidden md:flex md:justify-end" >
			<div>
				<div><UserComponent/></div>
			</div>
		</div>
	</div>

	{#if aberto}
		<Menu/>
	{/if}
</div>



