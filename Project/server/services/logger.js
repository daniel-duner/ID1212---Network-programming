function logger(req,re,next){
    console.log("Time", new Date(), req.method, req.path);
    next();
};

module.exports = logger;