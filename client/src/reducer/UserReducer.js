const UserReducer = (state, action) => {
    switch (action.type) {
        case "setUser":
            return action.payload;
        default:
            throw new Error("Unsupported action type");
    }

}

export default UserReducer;