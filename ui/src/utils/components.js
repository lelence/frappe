const loadView = (name) => () => import(`@/views/${name}/`)

export {
    loadView
}